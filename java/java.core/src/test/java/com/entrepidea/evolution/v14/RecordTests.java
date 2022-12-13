package com.entrepidea.evolution.v14;

import org.junit.Test;
/*
* Record is introduced since V.14. The purpose is to simplify the code for immunitabilities. e.g hashCode, equals or toString, etc come out-of-box.
* https://dev.java/learn/using-record-to-model-immutable-data/#accessors
* */
public class RecordTests {

    public record Point(int x, int y) {
        public int x() {
            return this.x;
        }

        public int y() {
            return this.y;
        }

    }

    @Test
    public void test(){}

    public record Range(int start, int end) {
        //compact constructor
        //The compact canonical constructor does not need to declare its block of parameters.
        public Range {
            if (end <= start) {
                throw new IllegalArgumentException("End cannot be lesser than start");
            }
        }
    }

}
