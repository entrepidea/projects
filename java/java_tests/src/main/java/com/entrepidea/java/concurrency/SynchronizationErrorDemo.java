package com.entrepidea.java.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

class ForwardingSet<E> implements Set<E>{

	public ForwardingSet(Set<E> s){};
	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

public class SynchronizationErrorDemo {

	public interface SetObserver<E>{
		public void added(ObservableSet<E> set, E element);
	}
	
	class ObservableSet<E> extends ForwardingSet<E>{
		
		public ObservableSet(Set<E> s){super(s);}
		private final List<SetObserver<E>> observers = new ArrayList<SetObserver<E>>();
		public void addObserver(SetObserver<E> observer){
			synchronized(observers){
				observers.add(observer);
			}
		}
		public boolean removeObserver(SetObserver<E> observer){
			synchronized(observers){
				return observers.remove(observer);
			}
		}
		private void notifyElementAdded(E element){
			synchronized(observers){
				for(SetObserver<E> observer:observers){
					observer.added(this, element);
				}
			}
		}
		@Override public boolean add(E element){
			boolean added = super.add(element);
			if(added)
				notifyElementAdded(element);
			return added;
		}
		@Override public boolean addAll(Collection<? extends E> elements){
			boolean result = false;
			for(E element: elements){
				result |= add(element);
			}
			return result;
		}
	}
	
	public static void main(String[] args){
		
	}
}
