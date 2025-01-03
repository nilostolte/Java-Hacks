package com.list;
import java.lang.System;
import java.nio.charset.Charset;

/**
 * 
 * <dd><b><code>FastCharArrayList</code></b> is an infinite array of bytes. 
 * It replaces a string with advantages, since it:<br><br>
 *
 * <ul> 
 * 		<li>allows easy appending of elements using
 *          <ul>
 *            <li><b><code>{@link #add(byte e)}</code></b>
 *            <li><b><code>{@link #add(char e)}</code></b>
 *          </ul>
 * 		<li>allows easy putting elements using 
 *          <ul>
 *            <li><b><code>{@link #put(byte e, int at)}</code></b>
 *            <li><b><code>{@link #put(char e, int at)}</code></b>
 *          </ul>
 * 		<li>allows easy writing of a sequence of bytes using 
 * 			<b><code>{@link #get()}</code></b> and using one of the space increasing methods:
 * 			<ul>
 *          <li><b><code>{@link #force(int length)}</code></b>
 *          <li><b><code>{@link #increase(int length)}</code></b>
 *          <li><b><code>{@link #force_increase(int length)}</code></b>
 *          </ul> 		
 * 		<li>allows deleting elements from the back using <b><code>{@link #delete()}</code></b>
 *          or <b><code>{@link #delete(int n)}</code></b>
 * 		<li>allows using any character encoding. If different than UTF-8, please use<br>
 *          <b><code>{@link #setCharset(Charset charset)}</code></b>
 * 		<li>several <b><code>copy</code></b> methods:
 * 			<ul>
 *          <li><b><code>{@link #copy()}</code></b>
 *          <li><b><code>{@link #copy(byte[] copy, int start)}</code></b>
 *          <li><b><code>{@link #copy(byte[] copy, int start, int end)}</code></b>
 *          <li><b><code>{@link #copy(byte[] copy, int at, int start, int end)}</code></b>
 *          <li><b><code>{@link #toString()}</code></b>
 *          <li><b><code>{@link #subArray(int start, int end)}</code></b>
 *          <li><b><code>{@link #put(byte[] chars, int at)}</code></b>
 *          <li><b><code>{@link #put(byte[] chars, int start, int end, int at)}</code></b>
 *          </ul>
 * 		<li>allows setting the contents with a <code>byte[]</code> or a part of a <code>byte[]</code>
 *          using one of the several <b><code>set</code></b> methods
 * 			<ul>
 *          <li><b><code>{@link #set(byte[] copy)}</code></b>
 *          <li><b><code>{@link #set(byte[] copy, int start, int end)}</code></b>  
 *          <li><b><code>{@link #set(byte[] chars, int start_chars, int end_chars, int start)}</code></b>
 *          </ul>          
 * </ul>
 * @author Nilo Stolte
 * </dd>
 */
public class FastByteArrayList {
    private byte[] elementData;
    private int size;
	private Charset charset;
    
    /**
     * <ul> Sets a particular charset instead of UTF-8 for conversion of the elements
     * of the internal array into strings<br><br>
     * @param charset
     * </ul>
     */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
     * <ul>Constructor that imposes an initial capacity to avoid frequent array reallocations
     * and sets UTF-8 as the default charset for conversion of the internal array into a
     * string.<br><br>
     * @param initialCapacity - size of the initial array
     * </ul>
     */
    public FastByteArrayList(int initialCapacity) {
       elementData = new byte[initialCapacity];
       charset = Charset.forName("UTF-8");
    }
    
    /**
     * <ul>Constructor that imposes an initial capacity to avoid frequent array reallocations 
     * and a particular charset instead of the default UTF-8 for conversion of the internal 
     * array into a string.<br><br>
     * @param initialCapacity - size of the initial array
     * </ul>
     */
    public FastByteArrayList(int initialCapacity, Charset charset) {
    	elementData = new byte[initialCapacity];
    	this.charset = charset;
    }
      
    private void grow() {
        byte[] copy =  new byte[size + (size>>1)];
        System.arraycopy(elementData, 0, copy, 0, size);
        elementData = copy;
    }
    
    
    /** <ul>Appends an element (byte) after the end of this array list <br><br>
     * 
     * @param e - byte to append
     * </ul>
     */
    public void add(byte e) {
        if ( size >= elementData.length ) grow();
        elementData[size++] = e;
    }
    
    /** <ul>Appends an element (char) after the end of this array list <br><br>
     * 
     * @param e - byte to append
     * </ul>
     */
    public void add(char e) {
        if ( size >= elementData.length ) grow();
        elementData[size++] = (byte) e;
    }
    
    /** <ul>Puts an element (byte) after at the position in the internal array<br><br>
     * 
     * @param e - byte to put
     * @param at - position to put the element in the internal array
     * </ul>
     */
    public void put(byte e, int at) {
        if ( at >= elementData.length ) return;
        elementData[at] = e;
    }
    
    /** <ul>Puts an element (char) after at the position in the internal array<br><br>
     * 
     * @param e - char to put
     * @param at - position to put the element in the internal array
     * </ul>
     */
    public void put(char e, int at) {
        if ( at >= elementData.length ) return;
        elementData[at] = (byte) e;
    }
      
    /** 
     * <ul>Allocates space in the internal array to be able to use it as a normal array 
     * with<br><br> <ul><code>public byte[] </code>{@link #get()}</ul>If the space in 
     * the existing array is insufficient, a new array is allocated with enough room, 
     * erasing the previous elements of the array. All elements <b>appended</b> will 
     * be placed after this space.<br><br>
     * @param length - size of the contents of the internal array
     * </ul>
     */
    public void force(int length) {
    	if (length > elementData.length) elementData = new byte[length];
    	size = length;
    }
    
    /** 
     * <ul>Sets the size of the internal array to <b><i>length</i></b>. If the
     * <b><i>length</i></b> is greater than the size of the internal array, size
     * is set to the current maximum capacity.<br><br>
     * @param length - size of the contents of the internal array
     * </ul>
     */
    public void set_length(int length) {
    	if (length > elementData.length) size = elementData.length;
    	else size = length;
    }
    
    /** 
     * <ul>Allocates space in the internal array to be able to use it as a normal array 
     * with<br><br> <ul><code>public byte[] </code>{@link #get()}</ul>If the space in 
     * the existing array is insufficient, only the maximum capacity of the array is
     * allocated<br><br>
     * @param length - the length to be added to the size of the internal array
     * </ul>
     */
    public void increase(int length) {
    	if ((size + length) > elementData.length) size = elementData.length;
    	else size += length;
    }
    
    /** 
     * <ul>Same as <code>{@link #increase(int length)}</code>, but if there is no more
     * room in the internal array it allocates enough space to accommodate the space increase, 
     * copying the previous content to the new array. This is different 
     * than <code>{@link #force(int length)}</code>, because the size in
     * <code>{@link #force(int length)}</code> is the total internal array size and here,
     * <b><i>length</i></b> is just the length added to the current internal array size.
     * Another difference with <code>{@link #force(int length)}</code> is that here
     * previous contents are kept intact.<br><br>
     * 
     * @param length - the length to be added to the size of the internal array
     * </ul>
     */
    public void force_increase(int length) {
    	if ((size + length) > elementData.length) {
    		byte[] bytes = new byte[size+length];
    		System.arraycopy(elementData, 0, bytes, 0, size);
    		elementData = bytes;
    	}
    	else size += length;
    }  
    /**
     * <ul>Copies <b><i>chars</i></b> array to the internal array<br><br>
     * @param chars - array to be copied
     * </br>
     */
    public void set(byte[] chars) {
    	if (chars.length > elementData.length) elementData = new byte[chars.length];
    	System.arraycopy(chars, 0, elementData, 0, chars.length);
    	size = chars.length;
    }
    
    /**
     * <ul>Copies <b><i>chars</i></b> array at position <b><i>start</i></b>, leaving empty the 
     * positions <b>0</b> to 
     * <b><i>start</i>-1</b> of the internal array <br><br>
     * @param chars - array to be copied
     * @param start - position where chars will be copied
     * </br>
     */
    public void set(byte[] chars, int start) {
    	if (chars.length > (elementData.length - start)) elementData = new byte[start + chars.length];
    	System.arraycopy(chars, 0, elementData, start, chars.length);
    	size = start + chars.length;
    }
    
    /**
     * <ul>Copies <b><i>chars</i></b><b>[<i>start_chars</i></b> to <b><i>end_chars</i> - 1]</b> 
     * at <b><i>start</i></b>, leaving empty the positions <b>0</b> to <b><i>start</i>-1</b> 
     * of the internal array <br><br>
     * @param chars - array to be copied
     * @param start_chars - position from which to copy in array chars
     * @param end_chars - last position to be copied, not including it
     * @param start - position where chars will be copied
     * </br>
     */
    public void set(byte[] chars, int start_chars, int end_chars, int start) {
    	int copiedsize = end_chars - start_chars;
    	if (copiedsize > ( elementData.length - start )) elementData = new byte[start + copiedsize];
    	System.arraycopy(chars, start_chars, elementData, start, copiedsize);
    	size = start + chars.length;
    }
    
    /**
     * <ul>Copies array <b><i>chars</i></b> at the end of the internal array, conserving its previous 
     * content<br><br>
     * @param chars
     * </ul>
     */
    public void append(byte[] chars) {
    	append(chars, 0, chars.length);
    }
    
    
    /**
     * <ul>Copies array <b><i>chars</i></b> at the end of the internal array, conserving the previous 
     * content<br><br>
     * @param chars - array that is appended at the end of the internal array
     * @param start - first position in <b><i>chars</i></b> to be copied
     * @param end - the position in <b><i>chars</i></b> after the last byte to be copied
     * </ul>
     */
    public void append(byte[] chars, int start, int end) {
    	int appendedsize = end - start;
    	int totalsize = size + appendedsize;
    	if (totalsize > elementData.length ) {
    		byte[] bytes = new byte[totalsize + (totalsize>>1)];
    		System.arraycopy(elementData, 0, bytes, 0, size);
    		elementData = bytes;
    	}
    	System.arraycopy(chars, start, elementData, size, appendedsize);
    	size += appendedsize;
    }
    
    /**
     * <ul>Copies <b><i>chars</i></b> array to the position <b><i>at</i></b> of the internal 
     * array. If the size overflows the size of the internal array, only the part that can be 
     * copied is really copied. If <b><i>at</i></b> falls beyond the end of the internal array, 
     * nothing is copied. No change in size is performed.<br><br>
     * @param chars - array to be copied
     * @param at - position from internal array, where the copied array starts
     * </ul>
     */
    public void put(byte[] chars, int at) {
    	int putsize = chars.length;
    	if ( putsize > (elementData.length - at)) putsize = elementData.length - at;
    	if ( putsize < 0 ) return;
    	System.arraycopy(chars, 0, elementData, at, putsize);
    }
    
    /**
     * <ul>Copies a piece of <b><i>chars</i></b> array starting at <b><i>start</i></b> 
     * and ending before <b><i>end</i></b> to the position <b><i>at</i></b> of the internal 
     * array. If the size overflows the size of the internal array, only the part that can be 
     * copied is really copied. If <b><i>at</i></b> falls beyond the end of the internal array, 
     * nothing is copied. No change in size is performed.<br><br>
     * @param chars - array to be copied
     * @param start - first position in <b><i>chars</i></b> to be copied
     * @param end - the position in <b><i>chars</i></b> after the last byte to be copied
     * @param at - position from internal array, where the copied array starts
     * </ul>
     */
    public void put(byte[] chars, int start, int end, int at) {
    	int putsize = end - start;
    	if ( putsize > (elementData.length - at)) putsize = elementData.length - at;
    	if ( putsize < 0 ) return;
    	System.arraycopy(chars, start, elementData, at, putsize);
    }
    
    /** <ul>Deletes one element at the end of the internal array<br><br>
     * 
     * @return the byte deleted
     * </ul>
     */
    public byte delete() {
    	byte d = 0;
    	if (size > 0) {
    		d = elementData[size-1];
    		size--;
    	}
		return d;
    }
    
    /** <ul>Deletes <b><i>n</i></b> elements from the end of the internal array.
     *      If <b><i>n</i></b> is greater or equal than the size of the internal 
     *      array, it is equivalent of calling <code>{@link #clear()}</code><br><br>
     * 
     * @param n - number of elements to delete
     * </ul>
     */
    public void delete(int n) {
    	if ( n < size ) size -= n;
    	else size = 0;
    }
    
    /** <ul> Gets the real internal array, not a copy. The useful space is only the one
     *       returned by the method <code>{@link #length()}</code> or changed by the method
     *       </code>{@link #force(int length)}<br><br>
     * 
     * @return the internal byte array
     * </ul>
     */
    public byte[] get() {
       return elementData;
    }
    
    /** 
     * <ul>Returns the occupied size of the internal array<br><br>
     * @return the occupied size of the internal array
     * </ul>
     */
    public int length() {
       return size;
    }
    
    /** 
     * <ul>Returns the entire size of the internal array<br><br>
     * @return the entire size of the internal array
     * </ul>
     */
    public int real_length() {
    	return elementData.length;
    }
    
    /**
     * <ul>
     * Sets internal occupied size to zero. Additions and appending to the 
     * internal array start again at its first position 
     * </ul>
     */
    public void clear(){
       size = 0;
    }

	/**
	 * <ul>Returns a new array that is a subset of the internal array<br><br>
	 * @param start - start position to be at position 0 at the returned array<br><br>
	 * @param end - the last byte of the returned array is at <br><b><i>end</i> - 1</b><br><br>
	 * @return - the subset of the internal array
	 * </ul>
	 */
    public byte[] subArray(int start, int end) {
		byte[] sub = new byte[end - start];
		int i, j = 0;
		for ( i = start; i < end; i++ ) {
			sub[j++] = elementData[i];
		}
		return sub;
	}
	
    
	/**
	 * <ul>Returns a string with the contents of the internal array, assuming a UTF-8
	 * encoding or another encoding by supplying the Charset through the method:
	 * {@link #setCharset(Charset charset)}
	 * <br><br>
	 * 
	 * @return string with the contents of the internal array
	 * </ul>
	 */
    @Override
    public String toString() {
		if (size == 0 ) return "";	
		return new String(elementData, 0, size, charset);
	}

	/**
	 * <ul>Copies the contents of internal array and returns the new array<br><br>
	 * @return a copy of the internal array
	 * </ul>
	 */
    public byte[] copy() {
		byte[] copy = new byte[size];
		copy(copy, 0, 0, size);
		return copy;
	}

    /**
     * <ul>Copies the internal array starting at <b><i>start</i></b> <br><br>
     * @param copy - array to copy to
     * @param start - position form internal array where the copied array starts
     * </ul>
     */
    public void copy(byte[] copy, int start) {
		copy(copy, start, 0, size);
	}
	
    /**
     * <ul>Copies the internal array starting at <b><i>start</i></b> and ending at 
     * <b><i>end</i> - 1</b><br><br>
     * @param copy - array to copy to
     * @param start - position from internal array, where the copied array starts
     * @param end - position from internal array, before where the copied array ends
     * </ul>
     */
	public void copy(byte[] copy, int start, int end) {
		copy(copy, start, 0, end);
	}
	
	/**
	 * <ul>Copies the internal array starting at <b><i>start2</i></b> and ending at 
     * <b><i>end2</i> - 1</b> to the position <b><i>start1</i></b> in <i>copy</i><br><br>
	 * @param copy - array to copy to
	 * @param at  - position in <i>copy</i> array, where the internal array will be copied to
	 * @param start  - position from internal array, where the copied array starts
	 * @param end  - position from internal array, before where the copied array ends
	 * </ul>
	 */
	public void copy(byte[] copy, int at, int start, int end) {
		System.arraycopy(elementData, start, copy, at, end - start);	
	}
}
