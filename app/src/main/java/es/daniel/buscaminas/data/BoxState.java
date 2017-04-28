package es.daniel.buscaminas.data;

public enum BoxState {
	NO_USED, USED, BLOCK;

	public static BoxState fromOrdinal(Integer ordinal) {
		switch (ordinal){
			case 0:
				return NO_USED;
			case 1:
				return USED;
			case 2:
				return BLOCK;
			default:
				return null;
		}
	}
}