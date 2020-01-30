import java.util.Scanner;

public class DN1<T> implements Stack<T>, Sequence<T>{
    private static final int DEFAULT_CAPACITY = 64;
    private T[] a;
    private int front, back, size;
    private static boolean pogoj;

    public static Sequence<Stack<String>> SEQUENCE = new DN1<>();

    public static void main(String[] args) throws CollectionException{
        //Initialize 42 stacks
        for (int i = 0; i < 42; i++) {
            Stack<String> stack = new DN1<>();
            SEQUENCE.add(stack);
        }

        //Local variables
        String first, second, even, ne, lt, le, eq, gt, ge;
        char c;
        int fact, rand;
        boolean run = false;

        //Input reading system
        Scanner sc1, sc2;
        String s1, s2 = "";
        sc1 = new Scanner(System.in);
        while (run ||  sc1.hasNextLine()) {
            //Reset special variables
            pogoj = false;

            if(run) {
                s1 = s2;
                run = false;
            }
            else
                s1 = sc1.nextLine();
            sc2 = new Scanner(s1);
            while (sc2.hasNext()) {

                if (run) {
                    break;
                }
                s2 = sc2.next();

                //Check if ?command
                if (s2.matches("^\\?.*")) {
                    s2 = s2.replace("?", "");
                    if (!pogoj)
                        continue;
                }

                //Check for other comands
                switch(s2) {
                    case "echo":
                        if (SEQUENCE.get(0).isEmpty())
                            System.out.println("");
                        else
                            System.out.println(SEQUENCE.get(0).top());
                        break;
                    case "pop":
                        SEQUENCE.get(0).pop();
                        break;
                    case "dup":
                        SEQUENCE.get(0).push(SEQUENCE.get(0).top());
                        break;
                    case "dup2":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).top();
                        SEQUENCE.get(0).push(first); 
                        SEQUENCE.get(0).push(second); 
                        SEQUENCE.get(0).push(first);
                        break;
                    case "swap":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(first);
                        SEQUENCE.get(0).push(second);
                        break;
                    case "char":
                        c = (char)Integer.parseInt(SEQUENCE.get(0).pop());
                        SEQUENCE.get(0).push(String.valueOf(c));
                        break;
                    case "even":
                        if (Integer.parseInt(SEQUENCE.get(0).pop()) % 2 == 0)
                            even = "1";
                        else
                            even = "0";
                        SEQUENCE.get(0).push(even);
                        break;
                    case "odd":
                        if (Integer.parseInt(SEQUENCE.get(0).pop()) % 2 == 0)
                            SEQUENCE.get(0).push("0");
                        else
                            SEQUENCE.get(0).push("1");
                        break;
                    case "!":
                        first = SEQUENCE.get(0).pop();
                        fact = 1;
                        for(int i = 1; i <= Integer.parseInt(first); i++)
                            fact = fact * i;
                        SEQUENCE.get(0).push(String.valueOf(fact));
                        break;
                    case "len":
                        first = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(String.valueOf(first.length()));
                        break;
                    case "<>":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        if (Integer.parseInt(first) != Integer.parseInt(second))
                            ne = "1";
                        else
                            ne = "0";
                            SEQUENCE.get(0).push(ne);
                        break;
                    case "<":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        if (Integer.parseInt(second) < Integer.parseInt(first))
                            lt = "1";
                        else
                            lt = "0";
                            SEQUENCE.get(0).push(lt);
                        break;
                    case "<=":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        if (Integer.parseInt(second) <= Integer.parseInt(first))
                            le = "1";
                        else
                            le = "0";
                            SEQUENCE.get(0).push(le);
                        break;
                    case "==":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        if (Integer.parseInt(first) == Integer.parseInt(second))
                            eq = "1";
                        else
                            eq = "0";
                            SEQUENCE.get(0).push(eq);
                        break;
                    case ">":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        if (Integer.parseInt(second) > Integer.parseInt(first))
                            gt = "1";
                        else
                            gt = "0";
                            SEQUENCE.get(0).push(gt);
                        break;
                    case ">=":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        if (Integer.parseInt(second) >= Integer.parseInt(first))
                            ge = "1";
                        else
                            ge = "0";
                            SEQUENCE.get(0).push(ge);
                        break;
                    case "+":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(String.valueOf(Integer.parseInt(second) + Integer.parseInt(first)));
                        break;
                    case "-":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(String.valueOf(Integer.parseInt(second) - Integer.parseInt(first)));
                        break;
                    case "*":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(String.valueOf(Integer.parseInt(second) * Integer.parseInt(first)));
                        break;
                    case "/":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(String.valueOf(Integer.parseInt(second) / Integer.parseInt(first)));
                        break;
                    case "%":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(String.valueOf(Integer.parseInt(second) % Integer.parseInt(first)));
                        break;
                    case ".":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).push(second + first);
                        break;
                    case "rnd":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        rand = (int)(Math.random() * (Integer.parseInt(first) - Integer.parseInt(second) + 1)) + Integer.parseInt(second);
                        SEQUENCE.get(0).push(String.valueOf(rand));
                        break;
                    case "then":
                        first = SEQUENCE.get(0).pop();
                        if (Integer.parseInt(first) != 0)
                            pogoj = true;
                        break;
                    case "else":
                        pogoj = !pogoj;
                        break;
                    case "print":
                        first = SEQUENCE.get(0).pop();
                        System.out.println(SEQUENCE.get(Integer.parseInt(first)).toString());
                        break;
                    case "clear":
                        first = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).clear(Integer.parseInt(first));
                        break;
                    case "reverse":
                        first = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).reverse(Integer.parseInt(first));
                        break;
                    case "move":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        SEQUENCE.get(0).move(Integer.parseInt(first), Integer.parseInt(second));
                        break;
                    case "fun":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        for (int i = 0; i < Integer.parseInt(second); i++) {
                            SEQUENCE.get(Integer.parseInt(first)).push(sc2.next());
                        }
                        break;
                    case "run":
                        first = SEQUENCE.get(0).pop();
                        s2 = SEQUENCE.get(0).run(first);
                        run = true;
                        while(sc2.hasNext()) {
                            s2 += " " + sc2.next();
                        }
                        break;
                    case "pogla":
                        pogoj = false;
                        break;
                    case "loop":
                        first = SEQUENCE.get(0).pop();
                        second = SEQUENCE.get(0).pop();
                        StringBuffer sb = new StringBuffer();
                        StringBuffer sbFinal = new StringBuffer();
                        String el;
                        int prvi = Integer.parseInt(first);
                        for (int i = 1; i < 42; i++) {
                            if (SEQUENCE.get(i).isEmpty() && i != prvi) {
                                while (!SEQUENCE.get(prvi).isEmpty()) {
                                    SEQUENCE.get(i).push(SEQUENCE.get(prvi).pop());
                                }
                                while (!SEQUENCE.get(i).isEmpty()) {
                                    el = SEQUENCE.get(i).pop();
                                    SEQUENCE.get(prvi).push(el);
                                    sb.append(" " + el);
                                }
                                break;
                            }
                        }
                        for (int i = 0; i < Integer.parseInt(second); i++) {
                            sbFinal.append(" pogla " + sb);
                        }
                        s2 = sbFinal.toString();
                        run = true;
                        while(sc2.hasNext()) {
                            s2 += " " + sc2.next();
                        }
                        break;
                    default:
                        SEQUENCE.get(0).push(s2);
                        break;
                }
            }
            if (!run){
                for (int i = 0; i < 41; i++) {
                    SEQUENCE.get(0).clear(i);
                }
            }
            

        }
        sc1.close();
    }

    @SuppressWarnings("unchecked")
    public DN1(){
        a = (T[])(new Object[DEFAULT_CAPACITY]);
        front = 0;
        back = 0;
        size = 0;
    }
    
    @Override
    public boolean isEmpty(){
        return size == 0;
    }
    
    @Override
    public boolean isFull(){
        return size == DEFAULT_CAPACITY;
    }
    
    @Override
    public int size(){
        return size;
    }
    
    public int next(int i){
        return (i+1)%DEFAULT_CAPACITY;
    }
    
    public int prev(int i){
        return (DEFAULT_CAPACITY+i-1)%DEFAULT_CAPACITY;
    }
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        if(size > 0){
            sb.append(a[front].toString());
        }
        for (int i = 0; i < size-1; i++) {
            sb.append(" " + a[next(front+i)]);
        }
        return sb.toString();
    }
    
    //Stack
    @Override
    public T top() throws CollectionException{
        if(isEmpty()){
            throw new CollectionException(ERR_MSG_EMPTY);
        }
        return a[prev(back)];
    }
    
    @Override
    public void push(T x) throws CollectionException{
        if(isFull()){
            throw new CollectionException(ERR_MSG_FULL);
        }
        a[back] = x;
        back = next(back);
        size++;
    }
    
    @Override
    public T pop() throws CollectionException {
        if(isEmpty())
            throw new CollectionException(ERR_MSG_EMPTY);
        back = prev(back);
        T o = a[back];
        a[back] = null;
        size--;
        return o;
    }

    @Override
    public void clear(int first) throws CollectionException {
        while(!SEQUENCE.get(first).isEmpty()){
            SEQUENCE.get(first).pop();
        }
    }

    @Override
    public void move(int first, int second) throws CollectionException{
        for (int i = 0; i < second; i++) {
            SEQUENCE.get(first).push(SEQUENCE.get(0).pop());
        }
    }

    @Override
    public void reverse(int first) throws CollectionException{
        int helpStack1 = 0;
        int helpStack2 = 0;
        for (int i = 1; i <= 42; i++) {
            if (SEQUENCE.get(i).isEmpty() && i != first){
                helpStack1 = i;
                break;
            }
        }
        for (int i = helpStack1 + 1; i <= 42; i++) {
            if (SEQUENCE.get(i).isEmpty() && i != first){
                helpStack2 = i;
                break;
            }
        }
        while (!SEQUENCE.get(first).isEmpty()) {
            SEQUENCE.get(helpStack1).push(SEQUENCE.get(first).pop());
        }
        while (!SEQUENCE.get(helpStack1).isEmpty()) {
            SEQUENCE.get(helpStack2).push(SEQUENCE.get(helpStack1).pop());
        }
        while (!SEQUENCE.get(helpStack2).isEmpty()) {
            SEQUENCE.get(first).push(SEQUENCE.get(helpStack2).pop());
        }
    }

    @Override
    public String run(String first) throws CollectionException{
        StringBuffer sb = new StringBuffer();
        String el;
        int prvi = Integer.parseInt(first);
        for (int i = 1; i < 42; i++) {
            if (SEQUENCE.get(i).isEmpty() && i != prvi) {
                while (!SEQUENCE.get(prvi).isEmpty()) {
                    SEQUENCE.get(i).push(SEQUENCE.get(prvi).pop());
                }
                while (!SEQUENCE.get(i).isEmpty()) {
                    el = SEQUENCE.get(i).pop();
                    SEQUENCE.get(prvi).push(el);
                    sb.append(" " + el);
                }
                return sb.toString();
            }
        }
        return "";
    }

    //Sequence
    private int index(int i){
        return (front+i)%DEFAULT_CAPACITY;
    
    }

    public void add(T x) throws CollectionException{
        push(x);
    }

    public T get(int i) throws CollectionException {
        if (i<0 || i >= size)
            throw new CollectionException(ERR_MSG_INDEX);
        return a[index(i)];
    }
}

interface Collection {
    static final String ERR_MSG_EMPTY = "Collection is empty.";
    static final String ERR_MSG_FULL = "Collection is full.";

    boolean isEmpty();
    boolean isFull();
    int size();
    String toString();
}

interface Stack<T> extends Collection {
    T top() throws CollectionException;
    void push(T x) throws CollectionException;
    T pop() throws CollectionException;
    void clear(int first) throws CollectionException;
    void move(int first, int second) throws CollectionException;
    void reverse(int first) throws CollectionException;
    String run(String first) throws CollectionException;
}

interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";
    T get(int i) throws CollectionException;
    void add(T x) throws CollectionException;
}

class CollectionException extends Exception {
    
    public CollectionException(String msg) {
        super(msg);
    }
}