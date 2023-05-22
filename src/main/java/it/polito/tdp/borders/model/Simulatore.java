package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	// Stato del sistema e output
	private Map<Country, Integer> stanziali ;
	

	// Parametri della simulazione
	private Graph<Country, DefaultEdge> graph ;
	private int nPersone = 1000 ;
	private Country partenza ;
	
	// Output
	private int nPassi ;
	
	// Coda degli eventi
	private PriorityQueue<Event> queue ;
	
	
	public Simulatore(Graph<Country, DefaultEdge> graph, Country partenza) {
		this.graph = graph ;
		this.partenza = partenza ;
		
		this.nPassi = 0 ;
		
		this.stanziali = new HashMap<Country, Integer>() ;
		for(Country c: this.graph.vertexSet()) {
			this.stanziali.put(c, 0) ;
		}
		
		this.queue = new PriorityQueue<>() ;
	}
	
	public void initialize() {
		this.queue.add(new Event( 0, this.partenza, this.nPersone ));
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll() ;
			System.out.println(e);
			int time = e.getTime() ;
			Country destinazione = e.getDestinazione() ;
			int dimensione = e.getDimensione() ;
			
			if(time>this.nPassi) {
				this.nPassi=time;
			}
			
			this.nPassi = time ;
			
			List<Country> vicini = Graphs.neighborListOf(this.graph, destinazione) ;
			int migranti = dimensione/2/vicini.size() ;
			System.out.println(destinazione.getStateAbb()+" ha "+vicini.size()+" confinanti");
			
			// dimensione / 2 si dividono negli stati adiacenti
			// generando eventi INGRESSO con la quota di persone
			if(migranti>0) {
				for(Country c : vicini) {
					this.queue.add(new Event(time+1, c, migranti)) ;
				}
			}

			// i rimanenti diventano stanziali nello stato 'destinazione'
			int nuovi_stanziali = dimensione - migranti * vicini.size() ;
			this.stanziali.put(destinazione, 
					this.stanziali.get(destinazione)+nuovi_stanziali) ;

		}
	}

	public int getnPassi() {
		return nPassi;
	}

	public Map<Country, Integer> getStanziali() {
		return stanziali;
	}


}