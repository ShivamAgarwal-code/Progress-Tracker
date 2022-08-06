package com.oppurtunity.hack.service;

import com.oppurtunity.hack.repositories.DonationRepository;
import java.util.ArrayList;
import java.util.List;
import com.oppurtunity.hack.entities.Donation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class DonationService {

	@Autowired
	private DonationRepository donationRepository;

	public List<Donation> getAll() {
		List<Donation> list = new ArrayList<>();
		list = (List<Donation>) donationRepository.findAll();
		return list;
	}

	public Donation save (Donation donation) {
		return donationRepository.save(donation);
	}
	
	
	public void addAllDonations(List<Donation> donations) {
		donationRepository.saveAll(donations);
	}

	public List<Donation> uploadFile(MultipartFile multipartFile) throws IOException {

		File file = convertMultiPartToFile(multipartFile);

		List<Donation> mandatoryMissedList = new ArrayList<Donation>();

		try (Reader reader = new FileReader(file);) {
			@SuppressWarnings("unchecked")
			CsvToBean<Donation> csvToBean = new CsvToBeanBuilder<Donation>(reader).withType(Donation.class)
					.withIgnoreLeadingWhiteSpace(true).build();
			List<Donation> donationList = csvToBean.parse();

			Iterator<Donation> donarListClone = donationList.iterator();

			while (donarListClone.hasNext()) {

				Donation donation = donarListClone.next();

				if (donation.getDonor() == null) {
					mandatoryMissedList.add(donation);
					donarListClone.remove();
				}
			}

			donationRepository.saveAll(donationList);
		}
		return mandatoryMissedList;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
}
