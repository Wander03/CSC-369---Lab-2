For each report, my map/reduce statements are very similar. My map statement pulls out the necessary information from
the Apache HTTP log file for the key, and similarly for the value (for bytes) or assigned the value 1 (otherwise). The
reduce statements for all of my requests are exactly the same, summing the values for similar keys. A few of the reports
require running a second map/reduce statement (reports 1, 4, 6). This statement reads in the output from the first, and
swaps the key and values (see the program KeyValueSwap).
