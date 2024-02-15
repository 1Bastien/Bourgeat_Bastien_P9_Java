package com.medilabo.patientApi.DataLoader;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.medilabo.patientApi.model.Gender;
import com.medilabo.patientApi.model.Patient;
import com.medilabo.patientApi.model.repository.PatientRepository;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(DataLoader.class);

	@Autowired
	private PatientRepository patientRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {

		try {
			logger.info("Deleting all patient data from database");
			patientRepository.deleteAll();

			logger.info("Adding patient data to database");

			Patient patientNone = new Patient();
			patientNone.setId(Long.valueOf(1));
			patientNone.setFirstName("TestNone");
			patientNone.setLastName("Test");
			patientNone.setDateOfBirth(LocalDate.parse("1966-12-31"));
			patientNone.setGender(Gender.F);
			patientNone.setAddress("1 Brookside St");
			patientNone.setPhoneNumber("100-222-3333");
			patientRepository.save(patientNone);

			Patient patientBoderline = new Patient();
			patientBoderline.setId(Long.valueOf(2));
			patientBoderline.setFirstName("TestBorderline");
			patientBoderline.setLastName("Test");
			patientBoderline.setDateOfBirth(LocalDate.parse("1945-06-24"));
			patientBoderline.setGender(Gender.M);
			patientBoderline.setAddress("2 High St");
			patientBoderline.setPhoneNumber("200-333-4444");
			patientRepository.save(patientBoderline);

			Patient patientInDanger = new Patient();
			patientInDanger.setId(Long.valueOf(3));
			patientInDanger.setFirstName("TestInDanger");
			patientInDanger.setLastName("Test");
			patientInDanger.setDateOfBirth(LocalDate.parse("2004-06-18"));
			patientInDanger.setGender(Gender.M);
			patientInDanger.setAddress("3 Club Road");
			patientInDanger.setPhoneNumber("300-444-5555");
			patientRepository.save(patientInDanger);

			Patient patientEarlyOnset = new Patient();
			patientEarlyOnset.setId(Long.valueOf(4));
			patientEarlyOnset.setFirstName("TestEarlyOnset");
			patientEarlyOnset.setLastName("Test");
			patientEarlyOnset.setDateOfBirth(LocalDate.parse("2002-06-28"));
			patientEarlyOnset.setGender(Gender.F);
			patientEarlyOnset.setAddress("4 Valley Dr");
			patientEarlyOnset.setPhoneNumber("400-555-6666");
			patientRepository.save(patientEarlyOnset);

		} catch (Exception e) {
			logger.error("Error while adding patient", e);
			throw new Exception("Error while adding patient");
		}
	}

}
