package edu.clemson.cs.cu.cpsc3720.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainDriver {

	private static ArrayList<Athlete> athletes;
	private static ArrayList<School> schools;
	private static ArrayList<Event> events;
	private static ArrayList<Teacher> teachers;
	private static ArrayList<Heat> heats;
	private static ArrayList<Registration> registrations;
	private static Scanner athleteCsv;
	private static Scanner heatCsv;
	private static Scanner eventCsv;

	private static void parseAthlete(String fileName, int columnCount) {
		try {
			athleteCsv = new Scanner(new File(fileName));
			Scanner rows = athleteCsv.useDelimiter("\n");

			while (rows.hasNext()) {
				Scanner row = rows.useDelimiter(",");
				while (row.hasNext()) {
					System.out.print(row.next() + " ");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eventCsv.close();
	}

	private static void parseHeat(String fileName, int columnCount) {
		try {
			heatCsv = new Scanner(new File(fileName));
			Scanner rows = heatCsv.useDelimiter("\n");

			while (rows.hasNext()) {
				Scanner row = rows.useDelimiter(",");
				while (row.hasNext()) {
					System.out.print(row.next() + " ");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eventCsv.close();
	}

	private static void parseEvent(String fileName, int columnCount) {
		try {
			eventCsv = new Scanner(new File(fileName));
			Scanner rows = eventCsv.useDelimiter("\n");

			while (rows.hasNext()) {
				Scanner row = rows.useDelimiter(",");
				while (row.hasNext()) {
					System.out.print(row.next() + " ");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		eventCsv.close();
	}

	private static void saveAthletes(ArrayList<Athlete> athletes) {

	}

	private static void saveHeats(ArrayList<Heat> heats) {

	}

	private static void saveEvents(ArrayList<Event> events) {

	}

	public static void main(String[] args) {
		athletes = new ArrayList<Athlete>();
		schools = new ArrayList<School>();
		events = new ArrayList<Event>();
		teachers = new ArrayList<Teacher>();
		heats = new ArrayList<Heat>();
		registrations = new ArrayList<Registration>();

		// Parse files
		// parseAthlete("athletes.csv", 10);
		// parseHeat("heats.csv", 7);
		parseEvent("events.csv", 6);

		// Save classes to db
		saveAthletes(athletes);
		saveHeats(heats);
		saveEvents(events);
	}
}
