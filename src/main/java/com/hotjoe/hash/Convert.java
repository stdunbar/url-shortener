package com.hotjoe.hash;

import com.hotjoe.util.urlshortner.Base62;

/*
declare
    l1 int;
    l2 int;
    r1 int;
    r2 int;
    i  int := 0;
begin
    l1 := (value >> 16) & 65535;
    r1 := value & 65535;
    while i < 3
        loop
            l2 := r1;
            r2 := l1 # ((((1366 * r1 + 150889) % 714025) / 714025.0) * 32767)::int;
            l1 := l2;
            r1 := r2;
            i := i + 1;
        end loop;
    return ((r1 << 16) + l1);
end;
 */
public class Convert {
    public static void main(String[] argv) {
        int l1, l2, r1, r2, i = 0;

        int value = 10000;

        l1 = (value >> 16) & 65535;
        r1 = value & 65535;

        while( i < 3 ) {
            l2 = r1;
            r2 = l1 ^ (int)((((((1366 * r1 + 150889) % 714025) / 714025.0) * 32767)));
            l1 = l2;
            r1 = r2;
            i++;
        }

        int output = ((r1 << 16) + l1);

        System.out.println( "value is " + Base62.idToShortURL(output));
    }
}
