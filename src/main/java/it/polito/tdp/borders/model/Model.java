package it.polito.tdp.borders.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private Graph<Country, DefaultEdge> graph;
	private Map<Integer, Country> countriesMap;
	
	private int nPassiSim ;

	public Model() {
		this.countriesMap = new HashMap<>();
	}

	public void creaGrafo(int anno) {

		this.graph = new SimpleGraph<>(DefaultEdge.class);

		BordersDAO dao = new BordersDAO();

		// vertici
		dao.getCountriesFromYear(anno, this.countriesMap);
		Graphs.addAllVertices(graph, this.countriesMap.values());

		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno);
		for (Adiacenza c : archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), this.countriesMap.get(c.getState2no()));

		}
	}

	public List<CountryAndNumber> getCountryAndNumbers() {
		List<CountryAndNumber> result = new LinkedList<>();

		for (Country c : this.graph.vertexSet()) {
			result.add(new CountryAndNumber(c, this.graph.degreeOf(c)));
		}

		Collections.sort(result);
		return result;
	}

	public Set<Country> getCountries() {

		if (this.graph != null) {
			return this.graph.vertexSet();
		}
		return null;

	}

	public Map<Country, Integer> simulaMigrazione(Country partenza) {
		Simulatore sim = new Simulatore(this.graph, partenza) ;
		sim.initialize() ;
		sim.run() ;
		this.nPassiSim = sim.getnPassi();
		return sim.getStanziali();
		
	}

	public int getnPassiSim() {
		return nPassiSim;
	}
}