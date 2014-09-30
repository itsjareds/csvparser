package edu.clemson.cs.cu.cpsc3720.main;

public class Registration implements DatabaseSerializable {

	private transient String dbId;
	private transient Event event;
	private String eventRef;
	private transient Athlete athlete;
	private String athleteRef;
	private Integer score;

	public Registration(String eventRef, String athleteRef, Integer score) {
		this.eventRef = eventRef;
		this.athleteRef = athleteRef;
		this.score = score;
	}

	public String getEventRef() {
		return eventRef;
	}

	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}

	public String getAthleteRef() {
		return athleteRef;
	}

	public void setAthleteRef(String athleteRef) {
		this.athleteRef = athleteRef;
	}

	/**
	 * @return the event
	 */
	public Event getEvent() {
		return this.event;
	}

	/**
	 * @return the athlete
	 */
	public Athlete getAthlete() {
		return this.athlete;
	}

	/**
	 * @return the score
	 */
	public Integer getScore() {
		return this.score;
	}

	/**
	 * @param event
	 *            the event to set
	 */
	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @param athlete
	 *            the athlete to set
	 */
	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}

	/**
	 * @param score
	 *            the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public String getDbId() {
		return this.dbId;
	}

	@Override
	public void setDbId(String id) {
		this.dbId = id;
	}
}
