package Utilities.aoc15;

import java.util.ArrayList;
import java.util.Objects;

public class HyperDuplex implements Comparable<HyperDuplex>{
        public int x = 0;
        public int y = 0;
        public HyperDuplex(int r, int c) {
            x = r;
            y = c;
        }

        public HyperDuplex() {
            x = 0;
            y = 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y,x);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            HyperDuplex other = (HyperDuplex) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }

        public void sumSelf(HyperDuplex o) {
            x += o.x;
            y += o.y;
        }

        public HyperDuplex sum(HyperDuplex o) {
            return new HyperDuplex(x + o.x, y + o.y);
        }

        public int dist(HyperDuplex o) {
            return Math.abs(x - o.x) + Math.abs(y - o.y);
        }

        public HyperDuplex copy() {
            return new HyperDuplex(x,y);
        }

        public HyperDuplex invert() {
            return new HyperDuplex(y,x);
        }

        public ArrayList<HyperDuplex> directNeighbors() {
            ArrayList<HyperDuplex> list = new ArrayList<HyperDuplex>();
            for(int yOff = -1; yOff < 2; yOff++) {
                for(int xOff = -1; xOff < 2; xOff++) {
                    //if not diagonal or self
                    if(xOff == 0 ^ yOff == 0) {
                        list.add(new HyperDuplex(x+xOff,y+yOff));
                    }
                }
            }
            return list;
        }

        public ArrayList<HyperDuplex> allNeighbors() {
            ArrayList<HyperDuplex> list = new ArrayList<HyperDuplex>();
            for(int yOff = -1; yOff < 2; yOff++) {
                for(int xOff = -1; xOff < 2; xOff++) {
                    //if not self
                    if(!(xOff == 0 && yOff == 0)) {
                        list.add(new HyperDuplex(x+xOff,y+yOff));
                    }
                }
            }
            return list;
        }

        @Override
        public int compareTo(HyperDuplex o) {
            if(this.equals(o))
                return 0;
            else if(o.y > this.y)
                return -1;
            else if(o.y < this.y)
                return 1;
            else
                return (o.x > this.x ? -1 : 1);
        }

    public static final HyperDuplex UP = new HyperDuplex(0,-1);
    public static final HyperDuplex DOWN = new HyperDuplex(0,1);
    public static final HyperDuplex LEFT = new HyperDuplex(-1,0);
    public static final HyperDuplex RIGHT = new HyperDuplex(1,0);

        public HyperDuplex left() {
            return new HyperDuplex(y,-x);
        }

        public HyperDuplex right() {
            return new HyperDuplex(-y,x);
        }

}
