package es.daniel.buscaminas.data;

public enum BoxState {
	NO_USED, USED, BLOCK, QUESTION;

	public static BoxState fromOrdinal(Integer ordinal) {
		return BoxState.values()[ordinal];
	}
}
