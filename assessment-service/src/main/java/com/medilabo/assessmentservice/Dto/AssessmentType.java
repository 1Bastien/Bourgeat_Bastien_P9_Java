package com.medilabo.assessmentservice.Dto;

public enum AssessmentType {

	NONE("None"), BORDERLINE("Boderline"), IN_DANGER("In Danger"), EARLY_ONSET("Early Onset");

	private final String type;

	private AssessmentType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
