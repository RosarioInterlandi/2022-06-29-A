package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	private SimpleDirectedWeightedGraph<Album, DefaultWeightedEdge> grafo;
	private ItunesDAO dao ;
	private Map<Integer, Album> IdMap;
	private Album source;
	private List<Album> bestPath;
	private int bestScore;
	
	public Model(){
		this.dao = new ItunesDAO();
		this.IdMap = new HashMap<>();
		this.popolaMappa();
	}
	public void popolaMappa() {
		List<Album> allAlbums =  dao.getAllAlbums();
		for (Album a: allAlbums) {
			IdMap.put(a.getAlbumId(), a);
		}
		return;
	}
	
	public void BuildGraph(Integer n) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);

		//Creazione vertici
		List<Album>allVertici =this.dao.getVertici(n, IdMap);
		Graphs.addAllVertices(this.grafo, allVertici);
		
		//Creazione edges
		for (Album a1: allVertici) {
			for (Album a2: allVertici) {
				int peso = a1.getnTrack()-a2.getnTrack();
				if (peso>0) {
					Graphs.addEdgeWithVertices(this.grafo, a2, a1, peso);
				}
				
			}
		}
		System.out.println("Numero vertci"+ this.grafo.vertexSet().size());
		System.out.println("Numero archi "+ this.grafo.edgeSet().size());
		
		
	}
	// per popolare la comboBox
	public List<Album> getAllAlbum(){
		List<Album> result = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(result);
		return result;
				
	}
	public int bilancioVertice(Album a){
		int bilancio = 0;
		List<DefaultWeightedEdge> archiEntranti = new ArrayList<>(this.grafo.incomingEdgesOf(a));
		List<DefaultWeightedEdge> archiUscenti = new ArrayList<>(this.grafo.outgoingEdgesOf(a));
		
		for (DefaultWeightedEdge d : archiEntranti) {
			bilancio += this.grafo.getEdgeWeight(d);
		}
		for (DefaultWeightedEdge d : archiUscenti) {
			bilancio -= this.grafo.getEdgeWeight(d);
		}
		return bilancio;
	}
	/**
	 * Creo una nuova classe composta dal nodo e dal suo peso(dato dal bilancio).
	 * @param root
	 * @return 
	 */
	public List<AdiacenzeBilanciate> calcolaAdiacenze (Album root){
		this.source = root;
		List<Album> successori = Graphs.successorListOf(this.grafo, root);
		List<AdiacenzeBilanciate> result = new ArrayList<>();
		
		for (Album a : successori) {
			int bilancio = this.bilancioVertice(a);
			AdiacenzeBilanciate ab = new AdiacenzeBilanciate(a, bilancio);
			result.add(ab);
		}
		
		Collections.sort(result);
		return result;
	}
	public List<Album> getPath(int PesoMinimo, Album target){
		List<Album> parziale = new ArrayList<>();
		this.bestPath = new ArrayList<>();
		PesoMinimo = PesoMinimo*60;
		parziale.add(source);
		this.ricorsione(parziale, target,PesoMinimo);
		
		return this.bestPath;
	}
	private void ricorsione(List<Album> parziale, Album target, int pesoMinimo) {
		Album current = parziale.get(parziale.size()-1);
		//Condizione uscita
		if(current.equals(target)) {
			if (getScore(parziale)>=this.bestScore) {
				this.bestScore = getScore(parziale);
				this.bestPath = new ArrayList<>(parziale);
			}
		}
		//Aggiungo elementi
		List<Album> successori = Graphs.successorListOf(this.grafo, current);
		for(Album possibile :successori) {
			if (this.grafo.getEdgeWeight(this.grafo.getEdge(current, possibile))>= pesoMinimo && !this.grafo.containsVertex(possibile)) {
				parziale.add(possibile);
				this.ricorsione(parziale, target, pesoMinimo);
				parziale.remove(parziale.size()-1);
			}
		}
	
	
	
	
	}
	
	public int getScore(List<Album> parziale) {
		int score = 0;
		for (Album a: parziale) {
			if (this.bilancioVertice(a)>= this.bilancioVertice(source)) {
				score +=1;
			}
		}
		return score;
	}
	
	
	
}
