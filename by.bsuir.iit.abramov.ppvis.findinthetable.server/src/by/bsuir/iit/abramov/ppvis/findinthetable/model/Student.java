package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private final String		name;
	private final Integer		group;
	private final List<Exam>	exams;

	public Student(final String name, final Integer group, final Exam... exams) {

		this.name = name;
		this.group = group;
		this.exams = new ArrayList<Exam>();
		for (final Exam exam : exams) {
			this.exams.add(exam);
		}
	}

	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Student other = (Student) obj;
		if (exams == null) {
			if (other.exams != null) {
				return false;
			}
		} else if (!exams.equals(other.exams)) {
			return false;
		}
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	public double getAverageMark() {

		double result = 0;
		int counter = 0;
		for (final Exam exam : exams) {
			if (exam.getMark() != null) {
				result += exam.getMark();
				counter++;
			}
		}
		result = result / counter;
		return result;
	}

	public final List<Exam> getExams() {

		return exams;
	}

	public final Exam getExams(final int num) {

		return num < exams.size() && num >= 0 ? exams.get(num) : null;
	}

	public final Integer getGroup() {

		return group;
	}

	public final String getName() {

		return name;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((exams == null) ? 0 : exams.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public double isExam(final String examName) {

		double result = -1;
		double counter = 0;
		for (final Exam exam : exams) {
			if (exam.getName() != null) {
				if (exam.getName().indexOf(examName) != -1) {
					result += exam.getMark() != null ? exam.getMark() : 0;
					counter++;
				}
			}
		}
		if (counter > 0) {
			result += 1;
			result = result / counter;
		}
		return result;
	}
}
