package com.medilabo.assessmentservice.Dto;

public enum Keywords {

	KEYWORD_1("Hémoglobine A1C"),
	KEYWORD_2("Microalbumine"),
	KEYWORD_3("Taille"), 
	KEYWORD_4("Poids"),
	KEYWORD_5("Fumeur"),
	KEYWORD_6("Fumeuse"),
	KEYWORD_7("Anormal"),
	KEYWORD_8("Cholestérol"),
	KEYWORD_9("Vertige"),
	KEYWORD_10("Rechute"),
	KEYWORD_11("Réaction"),
	KEYWORD_12("Anticorps");
	
	private final String keyword;

    Keywords(String keyword) {
        this.keyword = keyword;
    }
    
    public String getKeyword() {
        return keyword;
    }
}
