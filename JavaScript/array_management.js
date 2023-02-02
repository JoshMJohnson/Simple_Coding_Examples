/**
 * Array Management JavaScript:
 *  - Specifications
 *  - List Manipulation
 *  - Insertion
 *  - Deletion
 *  - Element Locating
 *  - Element Manipulation
 *  
 * Created By: Josh Johnson
 */

/**
 * Using the Better Comments extension 
 * 
 * TODO: A to do comment for future editing
 * * This is an important comment which highlights the line
 * ? Question/Double-check comment
 * ! Incorrect comment
 */

const list0 = []; // * empty list 

// * integer lists 
const list1 = [0]; 
const list2 = [4, 1];
const list3 = [-4, 6, 2]; 
const list4 = [0, 84, -3, 40, -21, 21];
const list5 = [1, 2, 3, 4, 5, 6, 7, 8, 9]; 
const list6 = [2, 4, 3, 2, 4, 2, 2]; 
const list7 = [1, 2.0, 3, 4, 5.2, 6, 7, 8.5, 9, 10.9, 11]; 

// * double lists 
const list8 = [3.5];
const list9 = [5, 3.6]; 
const list10 = [-1.7, 0, 6];
const list11 = [6.9, 7.1, -5, 3.7, -9.8]; 

// * number lists
const list12 = [1, 2.0, 3, 4, 5.5, 6.6, 7, 8, 9.9];

// * character lists 
const list13 = ['a', 'b', 'c'];

// * string lists 
const list14 = [""]; 
const list15 = ["hello"];
const list16 = ["Hello World"];
const list17 = ["hello world"]; 
const list18 = ["HELLO WORLD"];
const list19 = ["hello", "world"];
const list20 = ["hello", "world", "of", "coding", "!"];
const list21 = ["hello world of coding!"];
const list22 = ["cat", "dog", "cat", "dog", "dog", "guinea pig", "fish"];
const list23 = ["Discraft", "Innova", "Prodigy", "Axiom", "MVP", "Dynamic", "Gateway", "Latitude 64"];
const list24 = ["Vikings", "Packers", "Lions", "Bears"];
const list25 = ["Commander Cody", "Captain Rex", "Echo", "Commander Fox"];
const list26 = ["Anakin Skywalker", "Yoda", "Obi-Wan Kenobi", "Mace Windu", "Plo koon"];
const list27 = ["Anakin Skywalker", "Yoda", "Obi-Wan Kenobi", "Darth Sidious"];

// * object lists 
const list28 = [1, 2, 3.0, 4, "JJ", 6, "Air Plane"];

// * boolean lists 
const list29 = [true, false];

main();

/* main function */
function main() {
    /* specifications */
    console.log("\t\t\t\tSpecs");
    console.log("--------------------------------------");
    temp();

    console.log("\n\n**************************************");
    console.log("**************************************");
    console.log("**************************************\n\n");

    /* list manipulation */
    console.log("\t\t\tList Manipulation");
    console.log("--------------------------------------");
    join();

    console.log("\n\n**************************************");
    console.log("**************************************");
    console.log("**************************************\n\n");

    /* insertion */
    console.log("\t\t\t   Insertion");
    console.log("--------------------------------------");

    console.log("\n\n**************************************");
    console.log("**************************************");
    console.log("**************************************\n\n");

    /* deletion */
    console.log("\t\t\t   Deletion");
    console.log("--------------------------------------");
    pop();

    console.log("\n\n**************************************");
    console.log("**************************************");
    console.log("**************************************\n\n");

    /* element locating */
    console.log("\t\t\tElement Locating");
    console.log("--------------------------------------");

    console.log("\n\n**************************************");
    console.log("**************************************");
    console.log("**************************************\n\n");

    /* element manipulation */
    console.log("\t\t  Element Manipulation");
    console.log("--------------------------------------");
}

/* creating a copy of an array */
function temp() {
    let temp = list24.slice(); // makes a copy of list
    process.stdout.write("Temp: ");
    console.log(temp);

    console.log(`Temp: ${temp}`);
}

/* join method */
function join() {
    let temp = list24.map((x) => x); // makes a copy of list
    console.log(`join: ${temp.join(" * ")}`);
}

/* pop method */
function pop() {
    let temp = list24.slice();
    temp.pop();
    process.stdout.write("pop: ");
    console.log(temp);

}