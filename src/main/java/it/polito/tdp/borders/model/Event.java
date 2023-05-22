package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{

	private int time; //passi di simulazione
	//tipo di evento: INGRESSO(sottointeso)
	private Country destinazione;
	private int dimensione;
	
	public Event(int time, Country destinazione, int dimensione) {
		super();
		this.time = time;
		this.destinazione = destinazione;
		this.dimensione = dimensione;
	}

	public int getTime() {
		return time;
	}

	public Country getDestinazione() {
		return destinazione;
	}

	public int getDimensione() {
		return dimensione;
	}

	@Override
	public String toString() {
		return "Evento [time=" + time + ", destinazione=" + destinazione + ", dimensione=" + dimensione + "]";
	}

	@Override
	public int compareTo(Event o) {
		return this.time-o.time;
	}
	
}
