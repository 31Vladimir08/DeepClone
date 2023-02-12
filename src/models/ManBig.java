package models;

import java.util.List;

public class ManBig {
	public String name;
	public int age;
	public List<Man> men;

	public ManBig(String name, int age, List<Man> men) {
		this.name = name;
		this.age = age;
		this.men = men;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<Man> getFavoriteBooks() {
		return men;
	}

	public void setFavoriteBooks(List<Man> favoriteBooks) {
		this.men = favoriteBooks;
	}
}
