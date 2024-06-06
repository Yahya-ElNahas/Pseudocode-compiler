package osProject;

class Link {
	public Object data;
	public Link next;

	public Link(Object o) {
		this.data = o;
		this.next = null;
	}

	public String toString() {
		return data.toString();
	}
}

class LinkedList {
	Link head;

	public LinkedList() {
		head = null;
	}

	public void insertFirst(Object o) {
		Link newLink = new Link(o);
		newLink.next = head;
		head = newLink;
	}

	public Object removeFirst() {
		Object res = head.data;
		head = head.next;
		return res;
	}

	public Object getFirst() {
		return head.data;
	}

	public void insertLast(Object o) {
		Link newLink = new Link(o);
		if (head == null) {
			head = newLink;
			return;
		}
		Link current = head;
		while (current.next != null)
			current = current.next;
		current.next = newLink;
	}

	public Object removeLast() {
		if (head.next == null) {
			Object res = head.data;
			head = null;
			return res;
		}
		Link current = head;
		while (current.next.next != null)
			current = current.next;
		Object res = current.next.data;
		current.next = null;
		return res;
	}

	public Object getLast() {
		Link current = head;
		while (current.next != null)
			current = current.next;
		return current.data;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public String toString() {
		if (head == null)
			return "[ ]";
		String res = "[ " + head.data;
		Link current = head.next;
		while (current != null) {
			res += ", " + current.data;
			current = current.next;
		}
		res += " ]";
		return res;
	}
	
	public Object search(Object key)
	{
		Link current = head;
		boolean found = false;
		while(current != null) {
			String[] a = ((String)current.data).split(" ");
			if(a[0].equals((String)key)) {
				found = true;
				break;
			}
			current = current.next;
		}
		if(!found)
			return null;
		Object res = current.data;
		return res;
	}
	
	public int getSize()
	{
		int size = 0;
		Link current = head;
		while(current != null) {
			size++;
			current = current.next;
		}
		return size;
	}
	
	public Link searchLink(Object key)
	{
		Link current = head;
		boolean found = false;
		while(current != null) {
			String[] a = ((String)current.data).split(" ");
			if(a[0].equals((String)key)) {
				found = true;
				break;
			}
			current = current.next;
		}
		if(!found)
			return null;
		return current;
	}
	
	public void remove(Object o)
	{
		Link current = head;
		Link prev = null;
		if(current.next == null && current.data == o) {
			head = null;
			return;
		}
		while(current != null && current.data != o) {
			prev = current;
			current = current.next;
		}
		if(current == null)
			return;
		if(prev == null) {
			head = current;
			return;
		}
		prev.next = current.next;
	}
}





