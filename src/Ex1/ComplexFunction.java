package Ex1;

public class ComplexFunction implements complex_function {
    private function left;
    private function right;
    private Operation op;

    /**
     * A definitive constructor that initializes both functions to be Null and the operator to be None
     */
    public ComplexFunction() {
        this.left = null;
        this.right = null;
        this.op = Operation.None;
    }

    /**
     * @param op     string that marks a mathematical operation
     * @param fLeft  intended to be a left function
     * @param fRight intended to be a right function
     */
    public ComplexFunction(String op, function fLeft, function fRight) {
        op = op.toLowerCase();
        switch (op) {

            case "plus": {
                this.left = fLeft;
                this.right = fRight;
                this.op = Operation.Plus;
            }
            break;

            case "mul": {
                this.left = fLeft;
                this.right = fRight;
                this.op = Operation.Times;
            }
            break;

            case "div": {
                this.left = fLeft;
                this.right = fRight;
                this.op = Operation.Divid;
            }
            break;

            case "max": {
                this.left = fLeft;
                this.right = fRight;
                this.op = Operation.Max;
            }
            break;

            case "min": {
                this.left = fLeft;
                this.right = fRight;
                this.op = Operation.Min;
            }
            break;

            case "comp": {
                this.left = fLeft;
                this.right = fRight;
                this.op = Operation.Comp;
            }
            break;

            default:
                System.out.println("ERR:The operation is invalid");
                break;

        }

    }

    /**
     *constructor that accepts one function and puts it as the left function, the operator will be None and the right function will be null
     * @param fLeft intended to be a left function
     */
    public ComplexFunction(function fLeft)
    {
        this.left=fLeft;
        this.op=Operation.None;
        this.right=null;
    }

    /**
     * Copy constructor.
     * @param original The ComplexFunction object that needs to be copied
     */
    public ComplexFunction(ComplexFunction original)
    {
        this.op=original.getOp();
        this.left=original.left();
        this.right=original.right();
    }

    @Override
    public void plus(function f1) {
        if(this.getOp()==Operation.None)
        {
            this.right=f1;
            this.op=Operation.Plus;
        }
        else
        {
            this.left = new ComplexFunction(this);
            this.right = f1;
            this.op = Operation.Plus;
        }
    }

    @Override
    public void mul(function f1) {
        if(this.getOp()==Operation.None)
        {
            this.right=f1;
            this.op=Operation.Times;
        }
        else
        {
            this.left = new ComplexFunction(this);
            this.right = f1;
            this.op = Operation.Times;
        }
    }

    @Override
    public void div(function f1) {
        if(this.getOp()==Operation.None)
        {
            this.right=f1;
            this.op=Operation.Divid;
        }
        else
        {
            this.left = new ComplexFunction(this);
            this.right = f1;
            this.op = Operation.Divid;
        }
    }

    @Override
    public void max(function f1) {
        if(this.getOp()==Operation.None)
        {
            this.right=f1;
            this.op=Operation.Max;
        }
        else
        {
            this.left = new ComplexFunction(this);
            this.right = f1;
            this.op = Operation.Max;
        }
    }

    @Override
    public void min(function f1) {
        if(this.getOp()==Operation.None)
        {
            this.right=f1;
            this.op=Operation.Min;
        }
        else
        {
            this.left = new ComplexFunction(this);
            this.right = f1;
            this.op = Operation.Min;
        }
    }

    @Override
    public void comp(function f1) {
        if(this.getOp()==Operation.None)
        {
            this.right=f1;
            this.op=Operation.Comp;
        }
        else
        {
            this.left = new ComplexFunction(this);
            this.right = f1;
            this.op = Operation.Comp;
        }
    }

    /**
     * get left.
     * @return The left function from ComplexFunction
     */
    @Override
    public function left() { return this.left;}

    /**
     * get right.
     * @return The right function from ComplexFunction
     */
    @Override
    public function right() { return this.right; }

    /**
     * get op.
     * @return The Operation from ComplexFunction
     */
    @Override
    public Operation getOp() {
        return this.op;
    }

    public String toString()
    {
        StringBuilder s=new StringBuilder();
        s.append(this.opToString()+"("+this.left().toString()+","+this.right().toString()+")");
        return s.toString();
    }
    public String opToString()
    {
        switch (this.op)
        {
            case Max:
                return "max";
            case Min:
                return "min";
            case Comp:
                return "comp";
            case Plus:
                return "plus";
            case Divid:
                return "div";
            case Times:
                return "mul";
            case None:
               return "";
            default:
                return "error";

        }
    }

    @Override
    public double f(double x) {
        switch (this.op)
        {
            case Max:
                return Math.max(this.left.f(x),this.right.f(x));
            case Min:
                return Math.min(this.left.f(x),this.right.f(x));
            case Comp:
                return this.left.f(this.right.f(x));
            case Plus:
                return (this.left.f(x))+(this.right.f(x));
            case Divid:
                return (this.left.f(x))/(this.right.f(x));
            case Times:
                return (this.left.f(x))*(this.right.f(x));
            case None:
                return this.left.f(x);
            default:
                return 0;

        }
    }

    @Override
    public function initFromString(String s) {
        function ans = null;
        s = s.toLowerCase();
        String st = s.substring(0,s.indexOf("(")+1);
        int start = s.indexOf(st);
        if(start>=0 && s.endsWith(")")) {
            int commaInd = mainComma(s.substring(start+st.length(),s.length()-1));
            function left = initFromString(s.substring(start+st.length(),start+st.length()+commaInd));
            function right = initFromString(s.substring(start+st.length()+commaInd+1,s.length()-1));
            String op = st.substring(0,st.length()-1);
            ans = new ComplexFunction(op,left,right);
        }
        else {
            function a=new Polynom();
            ans = a.initFromString(s);
        }
        return ans;
    }

    /**
     * This static function returns the index of main comma (","),
     * in case of an invalid from returns -1;
     * @param s: represents a form
     * @return the index of the main comma (-1 if none).
     */
    private static int mainComma(String s) {
        int ans = -1;
        int c =0;
        boolean found = false;
        for(int i=0;i<s.length() && !found;i++) {
            char ch = s.charAt(i);
            if(ch=='(') {c++;}
            if(ch==')') {c--;}
            if(ch==',' && c==0) {
                ans = i;
                found=true;
            }
        }
        return ans;
    }


    @Override
    public function copy() {
        if(this instanceof ComplexFunction)
        {
            function f=new ComplexFunction(this);
            return f;
        }
        else
        {
            function f=this.copy();
            return f;
        }
    }

}
