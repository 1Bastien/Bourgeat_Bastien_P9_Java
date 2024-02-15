package com.medilabo.PatientFeedbackService.DataLoader;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.medilabo.PatientFeedbackService.model.Feedback;
import com.medilabo.PatientFeedbackService.model.repository.FeedbackRepository;

@Component
@Profile("!test")
public class DataLoader implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(DataLoader.class);

	@Autowired
	private FeedbackRepository patientFeedbackRepository;

	@Override
	public void run(String... args) throws Exception {
		try {
			logger.info("Deleting all feedback data from database");
			patientFeedbackRepository.deleteAll();

			logger.info("Adding feedback data to database");

			Feedback feedback1 = new Feedback();
			feedback1.setPatientId(Long.valueOf(1));
			feedback1.setPatientName("TestNone");
			feedback1.setDate(LocalDateTime.now());
			feedback1.setContent(
					"Le patient déclare qu'il 'se sent très bien' Poids égal ou inférieur au poids recommandé");
			patientFeedbackRepository.insert(feedback1);

			Feedback feedback2 = new Feedback();
			feedback2.setPatientId(Long.valueOf(2));
			feedback2.setPatientName("TestBorderline");
			feedback2.setDate(LocalDateTime.now());
			feedback2.setContent(
					"Le patient déclare qu'il ressent beaucoup de stress au travail Il se plaint également que son audition est anormale dernièrement");
			patientFeedbackRepository.insert(feedback2);

			Feedback feedback3 = new Feedback();
			feedback3.setPatientId(Long.valueOf(2));
			feedback2.setPatientName("TestBorderline");
			feedback3.setDate(LocalDateTime.now());
			feedback3.setContent(
					"Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois Il remarque également que son audition continue d'être anormale");
			patientFeedbackRepository.insert(feedback3);

			Feedback feedback4 = new Feedback();
			feedback4.setPatientId(Long.valueOf(3));
			feedback4.setPatientName("TestInDanger");
			feedback4.setDate(LocalDateTime.now());
			feedback4.setContent("Le patient déclare qu'il fume depuis peu");
			patientFeedbackRepository.insert(feedback4);

			Feedback feedback5 = new Feedback();
			feedback5.setPatientId(Long.valueOf(3));
			feedback5.setPatientName("TestInDanger");
			feedback5.setDate(LocalDateTime.now());
			feedback5.setContent(
					"Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière Il se plaint également de crises d’apnée respiratoire anormales Tests de laboratoire indiquant un taux de cholestérol LDL élevé");
			patientFeedbackRepository.insert(feedback5);

			Feedback feedback6 = new Feedback();
			feedback6.setPatientId(Long.valueOf(4));
			feedback6.setPatientName("TestEarlyOnset");
			feedback6.setDate(LocalDateTime.now());
			feedback6.setContent(
					"Le patient déclare qu'il lui est devenu difficile de monter les escaliers Il se plaint également d’être essoufflé Tests de laboratoire indiquant que les anticorps sont élevés Réaction aux médicaments");
			patientFeedbackRepository.insert(feedback6);

			Feedback feedback7 = new Feedback();
			feedback7.setPatientId(Long.valueOf(4));
			feedback7.setPatientName("TestEarlyOnset");
			feedback7.setDate(LocalDateTime.now());
			feedback7.setContent("Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps");
			patientFeedbackRepository.insert(feedback7);

			Feedback feedback8 = new Feedback();
			feedback8.setPatientId(Long.valueOf(4));
			feedback8.setPatientName("TestEarlyOnset");
			feedback8.setDate(LocalDateTime.now());
			feedback8.setContent(
					"Le patient déclare avoir commencé à fumer depuis peu Hémoglobine A1C upérieure au niveau recommandé");
			patientFeedbackRepository.insert(feedback8);

			Feedback feedback9 = new Feedback();
			feedback9.setPatientId(Long.valueOf(4));
			feedback9.setPatientName("TestEarlyOnset");
			feedback9.setDate(LocalDateTime.now());
			feedback9.setContent("Taille, Poids, Cholestérol, Vertige et Réaction");
			patientFeedbackRepository.insert(feedback9);

		} catch (Exception e) {
			logger.error("Error while adding feedback", e);
			throw new Exception("Error while adding feedback");
		}
	}
}
