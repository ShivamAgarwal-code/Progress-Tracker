(function() {
  var app = angular.module("mainApp");

  app.controller("PlotController", [
    '$scope',
    'EventService',
    'ObjectService',
    function($scope, EventService, ObjectService) {
      console.log("Reached plot controller");
      let margin = {
        top: 20,
        bottom: 30,
        left: 10,
        right: 10
      };
      let svgHeight = 500;
      let svgWidth = 600;
      let innerWidth = svgWidth - margin.left - margin.right;
      let innerHeight = svgHeight - margin.bottom - margin.top;
      let donations;
      let donors;
      // EventService.getDonations().then(function(response){
      //   donations = response.data;
      //   alertify.success("Successfuly imported data");
      //   ObjectService.getAllObjects().then(function(response){
      //     donors = response.data;
      //     alertify.success("Successfuly imported data");
      //     callback(donations, donors);
      //   }, function(err){
      //     alertify.error("Error importing the data");
      //     if(err){
      //       throw err;
      //     }
      //   })
      // }, function(err){
      //   alertify.error("Error importing the data");
      //   if(err){
      //     throw err;
      //   }
      // })

      d3.json("../data/donation.json").then(callback)



      function callback(data) {

        var color = d3.scaleLinear().domain([20, 40000]).range([0, 1]);
        var svg = d3.select('#bubble').append('svg')
          .attr('height', svgHeight)
          .attr('width', svgWidth)
          .attr('transform', 'translate(0,0)');


        svg.append('text')
          .attr('x', svgWidth / 2)
          .attr('y', margin.top)
          .attr("text-anchor", "middle")
          .style("font-size", "15px")
          .text("Donations denoted by circle size and color intensity");

        var g = svg.selectAll('g').data(data).enter().append('g');
        var scaleForRadius = d3.scaleSqrt().domain([20, 40000]).range([0, 50]); //Maximum count is 186
        var simulation = d3.forceSimulation()
          .force('force_x', d3.forceX(svgWidth / 2).strength(0.05))
          .force('force_y', d3.forceY(svgHeight / 2).strength(0.05))
          .force('anti_collide', d3.forceCollide(function(d) {
            return scaleForRadius(_.parseInt(d.amount)) + 10;
          }));


        var description = d3.select("body")
          .append("div")
          .style("position", "absolute")
          .style("z-index", "10")
          .style("visibility", "hidden")
          .style("color", "white")
          .style("padding", "8px")
          .style("background-color", "rgba(0, 0, 0, 0.75)")
          .style("border-radius", "6px")
          .style("font", "12px sans-serif")
          .text("tooltip");

        var circles = g.append('circle')
          .attr('class', 'player')
          .attr('r', function(d) {
            return scaleForRadius(_.parseInt(d.amount)); //for varying circle radius
          })
          .style("fill", function(d) {
            return d3.interpolateGreens(color(_.parseInt(d.amount)));
          })
          .on("mousemove", function() {
            return description.style("top", (d3.event.pageY - 10) + "px").style("left", (d3.event.pageX + 10) + "px");
          })
          .on("mouseout", function() {
            return description.style("visibility", "hidden");
          })
          .on('mouseover', function(d) {
            description.text(d.from);
            description.style("visibility", "visible");
          });

        function mouseOver(d, i) {
          d3.select(this).attr('fill', 'red');
        }

        function mouseOut(d, i) {

        }
        simulation.nodes(data).on('tick', updateNodes); // just for updating cx and cy randomly with every second.

        function updateNodes() {
          circles.attr('cx', function(d) {
              return d.x;
            })
            .attr('cy', function(d) {
              return d.y;
            });

          text.attr('x', function(d) {
              return d.x;
            })
            .attr('y', function(d) {
              return d.y
            });
        }

        var text = g.append("text")
          .attr("dy", ".2em")
          .style("text-anchor", "middle")
          .attr("font-family", "Gill Sans", "Gill Sans MT")
          .text(function(d) {
            return d.from;
          })
          .style("font-size", "0.6rem")
          .attr("fill", "black");
        circles.exit().remove();
        drawBar(data);
      }



      function drawBar(data) {
        d3.select('#pie').html('');
        d3.select('#drill').html('');
        var div = document.getElementById('drill');
        div.style.display = "block";

        var xScale = d3.scaleBand().rangeRound([margin.left, innerWidth]).padding(0.2);
        var yScale = d3.scaleLinear().rangeRound([innerHeight, margin.top]);
        xScale.domain(data.map(function(d) {
          return d.from;
        }));



        yScale.domain(makeYDomain());
        var yAxis = d3.axisLeft()
          .scale(yScale)
          .ticks(10);

        var xAxis = d3.axisBottom();

        var svg = d3.select('#drill').append('svg')
          .attr('id', 'interact')
          .attr('height', svgHeight)
          .attr('width', svgWidth)
          .attr('transform', 'translate(5,5)');

        svg.append('text')
          .attr('x', svgWidth - 200)
          .attr('y', margin.top)
          .attr("text-anchor", "middle")
          .style("font-size", "15px")
          .text('Amount donated by donors yet');
        svg.append('text')
          .attr('x', svgWidth / 2)
          .attr('y', svgHeight - 10)
          .attr("text-anchor", "middle")
          .style("font-size", "10px")
          .text("Figure - 2");


        var numberWins = d3.tip()
          .attr('class', 'tip')
          .offset([10, 100])
          .direction('s')
          .html(function(d) {
            return "<strong>Amount donated:</strong> <span style='color:red'>" + d.amount + "</span>";
          })

        svg.call(numberWins); //for tooltip
        var bars = svg.selectAll("bar")
          .data(data)
          .enter()
          .append("rect")
          .attr('class', 'bar')
          .attr('transform', 'translate(10,0)')
          .attr("x", function(d) {
            return xScale(d.from);
          })
          .attr("width", xScale.bandwidth())
          .attr("y", function(d) {
            return yScale(_.parseInt(d.amount));
          })
          .attr("height", function(d) {
            return innerHeight - yScale(_.parseInt(d.amount));
          })
          .on("mouseover", numberWins.show)
          .on("mouseout", numberWins.hide);



        svg.append("g")
          .attr("transform", "translate(0," + innerHeight + ")")
          .call(d3.axisBottom(xScale));

        svg.append("text")
          .attr("transform",
            "translate(" + (innerWidth - 50) + " ," +
            (innerHeight + margin.top + 20) + ")")
          .style("text-anchor", "middle")
          .text("");

        svg.append("g")
          .attr("transform", 'translate(20,0)')
          .attr("class", "axis")
          .call(yAxis)
          .append("text")
          .attr("transform", "rotate(-90)")
          .attr("x", -15)
          .attr("y", 2)
          .attr("dy", ".71em")
          .style("text-anchor", "end")
          .style("fill", "black")
          .text("Amount donated");


        function makeYDomain() {

          var temp = [];
          _.forEach(data, function(d) {
            temp.push(_.parseInt(d.amount));
          })
          return [0, d3.max(temp)];
        }

      }


    }
  ])

})();
