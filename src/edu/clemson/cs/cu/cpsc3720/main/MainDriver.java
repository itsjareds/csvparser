package edu.clemson.cs.cu.cpsc3720.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainDriver {

	private static DatabaseAccessObject<Athlete> athletes;
	private static DatabaseAccessObject<School> schools;
	private static DatabaseAccessObject<Event> events;
	private static DatabaseAccessObject<Teacher> teachers;
	private static DatabaseAccessObject<Heat> heats;
	private static DatabaseAccessObject<Registration> registrations;

	private static void parseAthlete(String fileName) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));

			if (scanner.hasNextLine())
				scanner.nextLine();

			while (scanner.hasNextLine()) {
				String row = scanner.nextLine();
				ArrayList<String> cols = new ArrayList<String>();
				for (String word : row.split(","))
					cols.add(parseCsvString(word));

				if (cols.size() >= 10) {
					String groupCode = cols.get(0);
					String schoolName = cols.get(1);
					String teacherName = cols.get(2);
					String firstName = cols.get(3);
					String lastName = cols.get(4);
					Integer age = Integer.parseInt(cols.get(5));
					String gender = cols.get(6);
					String eventCode = cols.get(7);
					String eventName = cols.get(8);
					Integer score = Integer.parseInt(cols.get(9));

					/* Generate teacher */
					Teacher t = null;
					int firstSpace = teacherName.indexOf(' ');
					String teacherFirstName = teacherName;
					String teacherLastName = "";
					if (firstSpace > -1) {
						teacherFirstName = teacherName.substring(0, firstSpace);
						teacherLastName = teacherName.substring(firstSpace + 1);
					}

					// check for duplicates
					for (Teacher teacher : teachers.objects) {
						if (teacher.getFirstName().equals(teacherFirstName)
								&& teacher.getLastName()
										.equals(teacherLastName)) {
							t = teacher;
							break;
						}
					}
					if (t == null) {
						t = new Teacher(teacherFirstName, teacherLastName,
								groupCode);
						teachers.objects.add(t);
					}
					teachers.save(t);

					/* Generate school */
					School s = null;

					// check for duplicates
					for (School school : schools.objects) {
						if (school.getSchoolName().equals(schoolName)) {
							s = school;
							break;
						}
					}
					if (s == null) {
						s = new School(schoolName);
						schools.objects.add(s);
					}
					schools.save(s);

					/* Generate initial registration */
					String eventRef = findEventFromCode(eventCode).getDbId();
					Registration r = new Registration(eventRef, null, score);

					/* Generate athlete */
					Athlete a = null;

					// check for duplicates
					for (Athlete athlete : athletes.objects) {
						if (athlete.getFirstName().equals(firstName)
								&& athlete.getLastName().equals(lastName)) {
							a = athlete;
							break;
						}
					}
					if (a == null) {
						a = new Athlete(t.getDbId(), firstName, lastName, age,
								gender, s.getDbId(), new ArrayList<String>());
						a.setRegistrations(new ArrayList<Registration>());
						athletes.objects.add(a);
					}

					// Add registration to athlete (update with refs later)
					registrations.objects.add(r);
					if (!a.getRegistrations().contains(r))
						a.getRegistrations().add(r);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	private static void parseHeat(String fileName) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));

			if (scanner.hasNextLine())
				scanner.nextLine();

			while (scanner.hasNextLine()) {
				String row = scanner.nextLine();
				ArrayList<String> cols = new ArrayList<String>();
				for (String word : row.split(","))
					cols.add(parseCsvString(word));

				if (cols.size() >= 7) {
					String eventCode = cols.get(0);
					String eventName = cols.get(1);
					String gender = cols.get(2);
					Integer minAge = Integer.parseInt(cols.get(3));
					Integer maxAge = Integer.parseInt(cols.get(4));
					String time = cols.get(5);
					Integer numHeats = Integer.parseInt(cols.get(6));

					String eventRef = findEventFromCode(eventCode).getDbId();

					heats.objects.add(new Heat(eventRef, gender, minAge,
							maxAge, time, numHeats));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	private static void parseEvent(String fileName) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(fileName));

			if (scanner.hasNextLine())
				scanner.nextLine();

			while (scanner.hasNextLine()) {
				String row = scanner.nextLine();
				ArrayList<String> cols = new ArrayList<String>();
				for (String word : row.split(","))
					cols.add(parseCsvString(word));

				if (cols.size() >= 6) {
					String eventCode = cols.get(0);
					String eventName = cols.get(1);
					String scoreUnit = cols.get(2);
					Integer scoreMin = Integer.parseInt(cols.get(3));
					Integer scoreMax = Integer.parseInt(cols.get(4));
					Integer sortSeq = Integer.parseInt(cols.get(5));

					events.objects.add(new Event(eventCode, eventName,
							scoreUnit, scoreMin, scoreMax, sortSeq));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}

	public static void main(String[] args) {
		athletes = new DatabaseAccessObject<Athlete>();
		schools = new DatabaseAccessObject<School>();
		events = new DatabaseAccessObject<Event>();
		teachers = new DatabaseAccessObject<Teacher>();
		heats = new DatabaseAccessObject<Heat>();
		registrations = new DatabaseAccessObject<Registration>();

		System.out.println("Parsing events...");
		parseEvent("events.csv");
		System.out.println("Saving events...");
		for (Event e : events.objects) {
			events.save(e);
		}

		System.out.println("Parsing heats...");
		parseHeat("heats.csv");
		System.out.println("Saving heats...");
		for (Heat h : heats.objects) {
			heats.save(h);
		}

		System.out.println("Parsing athletes...");
		parseAthlete("athletes.csv");
		System.out.println("Saving athletes...");
		for (Athlete a : athletes.objects) {
			athletes.save(a);
			for (Registration r : a.getRegistrations()) {
				r.setAthleteRef(a.getDbId());
				registrations.save(r);
				a.getRegRefs().add(r.getDbId());
				athletes.save(a);
			}
		}

		DatabaseAccessObject.close();
	}

	private static String parseCsvString(String s) {
		return s.trim().replaceAll("\"|'", "");
	}

	private static Event findEventFromCode(String eventCode) {
		Event ret = null;
		for (Event e : events.objects) {
			if (e.getEventCode().equals(eventCode)) {
				ret = e;
				break;
			}
		}
		return ret;
	}
}
