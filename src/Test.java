


abstract class Test {
    public final int cost;
    public static int COST_STATIC = 0;

    public Test(
        int cost
    ) {
        this.cost = cost;
    }

    public static int getCost() {
        return COST_STATIC;
    }
}


class Test2 extends Test {
    public static int COST_STATIC = 123;
    public Test2(
    ) {
        super(123);
    }
}


class Test3 extends Test {
    public static int COST_STATIC = 456;
    public Test3(
        int cost
    ) {
        super(456);
    }
}


class Test4 {
    public static void main(String[] args) {
        
        double distanceFromTowerCenter = 3;
        int number = 0;
        int total = 2;
        double x = Math.cos(2 * Math.PI * number / total + Math.PI / 2) * distanceFromTowerCenter;
        double y = Math.sin(2 * Math.PI * number / total + Math.PI / 2) * distanceFromTowerCenter;
        System.out.println(x + " " + y);
    }
}
