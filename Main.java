// Bowling Program B5
// Stephen Reader
// 10552526

//Imported
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // ************** Getting file name and storing values **************

        // Initialize ArrayList used to store numbers from file
        ArrayList numArray = new ArrayList();

        // Get file name from user
        Scanner fileInput = new Scanner(System.in);
        System.out.print("Please enter your .txt file (Example: b1.txt): ");
        String theFile = fileInput.next();
        fileInput.close();

        // Try and get the array list using readFile function
        try
        {
            numArray = readFile(theFile);
            //System.out.println(numArray);
        } catch (IOException exc)
        {
            System.out.println("I/O Error: " + exc);
        }


        // *** Now put user info into different arrays ***

        // Get number of players and create arrayList of that size
        int num_players = (int)numArray.get(0);
        ArrayList<ArrayList<Integer>> playersArr = new ArrayList<>();

        // Create new arrayLists inside the player arrayList to hold players scores
        for (int i = 0; i < num_players - 1; i++) {

            ArrayList score = new ArrayList();
            playersArr.add(score);
        }
        ArrayList score = new ArrayList();
        playersArr.add(score);

        // Loop through values and place into arrayList of arrayList for each player
        int frame = 0;
        int who = 0;

        for (int i = 1; i < numArray.size(); i++)
        {
            // Determine who's turn it is
            who = who % num_players;

            // If who is equal to 0 then a new frame has started
            if (who % num_players == 0)
            {
                frame++;
            }

            // Checks if we are in frames 1-9
            if (frame < 10)
            {
                // Strike
                if ((int) numArray.get(i) == 10)
                {
                    playersArr.get(who % num_players).add((int) numArray.get(i));
                }
                // Spare or Open
                else
                {
                    // Add the 2 rolls
                    playersArr.get(who % num_players).add((int) numArray.get(i));
                    playersArr.get(who % num_players).add((int) numArray.get(i + 1));
                    i++;
                }
            }
            else if(frame == 10)
            {
                playersArr.get(who % num_players).add((int)numArray.get(i));
                playersArr.get(who % num_players).add((int)numArray.get(i+1));

                // Checks for an extra roll (if needed)
                if ( (int)numArray.get(i) + (int)numArray.get(i+1) >= 10)
                {
                    playersArr.get(who % num_players).add((int)numArray.get(i+2));
                    i++;
                }
                i++;
            }
            who++;
        }


        // *** Score Output ***

        // Because there is an unknown number of users until the user gives a file, use loop to help with output
        for(int i = 0; i < num_players; i++)
        {
            int x = i +1;
            System.out.println("Player " + x + " score: " + scoreBowler(playersArr.get(i)));
        }
    }// End main()

    // Function to give a bowler a score
    public static int scoreBowler(ArrayList<Integer> numArray)
    {
        // Need a way to keep count of what frame the bowler is in and set score to 0 for start of game
        int frame = 0;
        int score = 0;
        int for_tenth = 0;

        // Loop through array
        for(int i = 0; i < numArray.size(); i++)
        {
            // Make sure that the frame is 1-9
            if(frame < 9)
            {
                // If a throw is a strike
                if(numArray.get(i) == 10)
                {
                    score = score + numArray.get(i) + numArray.get(i+1) + numArray.get(i+2);
                    frame++;
                    for_tenth++;
                }
                // If throw is a spare
                else if(numArray.get(i) != 0 && numArray.get(i+1) != 0 && numArray.get(i) + numArray.get(i+1) == 10 && frame < 10)
                {
                    score = score + numArray.get(i) + numArray.get(i+1) + numArray.get(i+2);
                    frame++;
                    i++;
                    for_tenth = for_tenth +2;
                }
                // If throw is not a strike or a spare
                else
                {
                    score = score + numArray.get(i) + numArray.get(i+1);
                    frame++;
                    i++;
                    for_tenth = for_tenth + 2;
                }
            }
        }// End for loop (frame 1-9)

        // 10th frame
        try
        {
            score = score + numArray.get(for_tenth) + numArray.get(for_tenth+1) + numArray.get(for_tenth+2);
        }
        catch (IndexOutOfBoundsException e)
        {
            score = score + numArray.get(for_tenth) + numArray.get(for_tenth+1);
        }

        // Return the score
        return score;
    }// End computeScore


    // Function to get the parts of the file into the array list
    public static ArrayList<Integer> readFile (String theFile)throws IOException
    {
        // Create file
        File setf = new File(theFile);

        // Use scanner to get into file
        Scanner scanner = new Scanner(setf);

        // ArrayList to hold the numbers in the file
        ArrayList numArray = new ArrayList();

        // Loop to insert into the ArrayList
        while (scanner.hasNextInt())
        {
            // Add int to ArrayList
            numArray.add(scanner.nextInt());
        }
        // Done with scanner
        scanner.close();

        // Return array
        return numArray;
    }// End readFile function
}// End class Main