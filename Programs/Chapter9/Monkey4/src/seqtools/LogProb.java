/*
 * Copyright 2013 Peter Garst.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package seqtools;

/**
 *
 * @author pgarst
 */
public class LogProb {

    private NGramModel mod;
    private int nsym = 0;
    private double sum = 0;

    public LogProb(NGramModel mod) {

        this.mod = mod;
    }

    public void add(String fname) {

        char[] alphabet = mod.getAlphabet();
        DataSource dsource = new DataSource(fname, alphabet);
        add(dsource);
    }

    public void add(CharGenerator dsource) {

        char[] alphabet = mod.getAlphabet();
        boolean initialized = mod.reinit();
        while (dsource.hasNextChar()) {
            int cn = dsource.nextChar();
            if (initialized) {
                nsym++;
                sum += mod.logProb(cn);
            } else {
                initialized = mod.initProb(alphabet[cn]);
            }
        }
    }

    public double prob() {

        if (nsym == 0)
            return 0;

        double lp = sum / nsym;
        return Math.exp(lp);
    }

    @Override
    public String toString() {

        if (nsym == 0)
            return "0";

        double lp = sum / nsym;
        String v1 = String.format("%6.3f", lp);
        String v2 = String.format("%6.3f", Math.exp(lp));
        return "Log " + v1 + "  value " + v2;
    }

}
