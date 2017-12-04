package bots;

import pirates.*;


public class MyBot implements PirateBot
{
    public void doTurn(PirateGame game)
    {
        //All the pirates that are defendin
        Pirate defPirates[] = game.getAllMyPirates();
        Pirate enemyPiratesDef[] = game.getEnemyLivingPirates();
        Location start;
        Location push;

        Pirate[] pirates = game.getAllMyPirates();
        Pirate[] enemyPirates = game.getEnemyLivingPirates();
        Mothership mothership = game.getMyMothership(); //create mothership
        Capsule capsule = game.getMyCapsule();
        boolean flag = true;


        if (game.getMyMothership().location.col == 500)
        {
            start = new Location(3405, 5332);
            push = new Location(4672, 6342);
        } else
        {
            start = new Location(3405, 1142);
            push = new Location(4672, 0);
        }


        //this for loop tells four of the pirates to go to the enemy's mother ship
        System.out.println(defPirates[4].location != start);

        boolean flag1 = true;
        for (int k = 0; k < game.getEnemyLivingPirates().length; k++)
        {
            System.out.println("" + (enemyPiratesDef[k].hasCapsule()));
            for (int i = 4; i <= 7; i++)
            {
                if (enemyPiratesDef[k].hasCapsule() && defPirates[i].canPush(enemyPiratesDef[k]))
                {

                    defPirates[i].push(enemyPiratesDef[k], push);
                    flag1 = false;

                }
            }

        }
        if (flag1)
        {
            defPirates[4].sail(start); //ships sailing towards the mothership of the enemy
            defPirates[5].sail(start);
            defPirates[6].sail(start);
            defPirates[7].sail(start);
        }
/////////////////////////////////// --ATTACK---///////////////////////////////////////////////////////////////////
        for (int i = 0; i <= 3; i++)
        {
            if (game.getMyMothership().location.col < 2000)
            {
                if (tryPush(pirates[i], game, new Location(0, 0)))
                    flag = false;
            } else
            {
                if (tryPush(pirates[i], game, new Location(6400, 6400)))
                    flag = false;
            }

        }

        if (flag)
        {
            // if no one has the capsule, sails to the capsule
            if (getCapsulePirate(game) == null)
            {
                for (int i = 0; i <= 3; i++)
                {
                    pirates[i].sail(game.getMyCapsule());
                }
            } else //else, everyone but the capsule sails to mothership
            {
                for (int i = 0; i <= 3; i++)
                {
                    if (!pirates[i].hasCapsule())
                    {
                        if (game.getMyMothership().location.col < 2000)
                            pirates[i].sail(new Location(3100, 845));
                        else
                            pirates[i].sail(new Location(3100, 5440));
                    }
                    else
                    {
                        pirates[i].sail(game.getMyMothership());
                    }
                }
            }
        }
            }


    private Pirate getCapsulePirate(PirateGame game)
    {
        for (Pirate pr : game.getMyLivingPirates())
        {
            if (pr.hasCapsule())
                return pr;
        }
        return null;
    }

    private boolean tryPush(Pirate pirate, PirateGame game, Location loc)
    {
        // Go over all enemies.
        for (Pirate enemy : game.getEnemyLivingPirates())
        {
            // Check if the pirate can push the enemy.
            if (pirate.canPush(enemy))
            {
                // Push enemy!
                pirate.push(enemy, loc);

                // Did push.
                return true;
            }
        }

        // Didn't push.
        return false;

    }


}
