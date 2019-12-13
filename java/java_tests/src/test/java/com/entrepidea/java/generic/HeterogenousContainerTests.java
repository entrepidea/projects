package com.entrepidea.java.generic;

/**
 * this item is about the typesafe of heterogeneous containers. When the value types of the container is more than 
 * type, we can parameterize the keys instead of values to achieve type-safty.
 * @see item #29, EJ, "Consider typesafe heterogeneous containers"
 * 
 * @note: 1. you can use class objects as keys for typesafe heterogeneous containers. The class object used in such a fashion is 
 * called type token;
 * 2. The class "Class" has been generified since 1.6 - it's no longer Class, it's Class<T> 
 *     
 * */
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.Assert;

public class HeterogenousContainerTests {
	
	
	class Favorites{
		
		private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();
		
		public<T> void putFavorite(Class<T> type, T favorite){
			if(null==type){
				throw new NullPointerException("The type is null");
			}
			favorites.put(type, favorite);
		};
		
		public<T> T getFavorite(Class<T> type){
			return type.cast(favorites.get(type));
		};
	}
	
	@Test
	public void testFavorite(){
		Favorites fav = new Favorites();
		fav.putFavorite(String.class, "I love JAVA");
		fav.putFavorite(Integer.class, 0xcafebabe);
		fav.putFavorite(Class.class, Favorites.class);
		
		System.out.println(fav.getFavorite(String.class));
		System.out.println(fav.getFavorite(Integer.class));
		System.out.println(fav.getFavorite(Class.class));
		
		Assert.assertTrue(fav.getFavorite(Integer.class) == 0xcafebabe);
		Assert.assertTrue(fav.getFavorite(Class.class)== Favorites.class);
	}
}
