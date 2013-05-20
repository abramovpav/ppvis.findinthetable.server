package by.bsuir.iit.abramov.ppvis.findinthetable.model;

import java.io.Serializable;

public class Exam implements Serializable {

	private static final long	serialVersionUID	= 1L;
	private final String		name;
	private final Integer		mark;

	public Exam(final String name, final Integer mark) {

		this.name = name;
		this.mark = mark;
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
		final Exam other = (Exam) obj;
		if (mark == null) {
			if (other.mark != null) {
				return false;
			}
		} else if (!mark.equals(other.mark)) {
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

	public Integer getMark() {

		return mark;
	}

	public String getName() {

		return name;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean isEmpty() {

		return (name == null && mark == null) || "" == name;
	}

}
