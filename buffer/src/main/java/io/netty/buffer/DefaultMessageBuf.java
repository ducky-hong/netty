/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.buffer;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Default {@link MessageBuf} implementation
 *
 */
final class DefaultMessageBuf<T> extends LinkedList<T> implements MessageBuf<T> {

    private static final long serialVersionUID = 1229808623624907552L;

    private boolean freed;

    DefaultMessageBuf() { }

    DefaultMessageBuf(int initialCapacity) {
        super();
    }

    @Override
    public BufType type() {
        return BufType.MESSAGE;
    }

    @Override
    public void addFirst(T t) {
        ensureValid();
        super.addFirst(t);
    }

    @Override
    public void addLast(T t) {
        ensureValid();
        super.addLast(t);
    }

    @Override
    public T pollFirst() {
        ensureValid();
        return isEmpty() ? null : removeFirst();
    }

    @Override
    public T pollLast() {
        ensureValid();
        return isEmpty() ? null : removeLast();
    }

    @Override
    public T getFirst() {
        ensureValid();
        return super.getFirst();
    }

    @Override
    public T getLast() {
        ensureValid();
        return super.getLast();
    }

    @Override
    public T peekFirst() {
        ensureValid();
        return super.peek();
    }

    @Override
    public T peekLast() {
        ensureValid();
        return isEmpty() ? null : super.getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        ensureValid();
        return super.remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        ensureValid();
        int i = lastIndexOf(o);
        if (i == -1) {
            return false;
        }
        return super.remove(i) != null;
    }

    @Override
    public Iterator<T> iterator() {
        ensureValid();
        return super.iterator();
    }

    @Override
    public Iterator<T> descendingIterator() {
        ensureValid();
        return super.descendingIterator();
    }

    @Override
    public boolean contains(Object o) {
        ensureValid();
        return super.contains(o);
    }

    @Override
    public void clear() {
        ensureValid();
        super.clear();
    }

    @Override
    public Object[] toArray() {
        ensureValid();
        return super.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        ensureValid();
        return super.toArray(a);
    }

    @Override
    public LinkedList<T> clone() {
        ensureValid();
        return (LinkedList<T>) super.clone();
    }

    @Override
    public int drainTo(Collection<? super T> c) {
        ensureValid();
        int cnt = 0;
        for (;;) {
            T o = poll();
            if (o == null) {
                break;
            }
            c.add(o);
            cnt ++;
        }
        return cnt;
    }

    @Override
    public int drainTo(Collection<? super T> c, int maxElements) {
        ensureValid();
        int cnt = 0;
        while (cnt < maxElements) {
            T o = poll();
            if (o == null) {
                break;
            }
            c.add(o);
            cnt ++;
        }
        return cnt;
    }

    @Override
    public boolean isFreed() {
        return freed;
    }

    @Override
    public void free() {
        freed = true;
        super.clear();
    }

    private void ensureValid() {
        if (freed) {
            throw new IllegalBufferAccessException();
        }
    }
}
