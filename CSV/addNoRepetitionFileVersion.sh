#!/bin/bash
for x in *.csv; do sudo nice -n -20 awk '!x[$0]++' $x>$x.NOREPETITION ;done #ADD FILES WITH NO LINES REPS


