package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
    boolean debugMode;
    public boolean enterPressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        //TITLE STATE----------------------
        if(gp.gameState == gp.titleState){

            if(gp.ui.titleScreenState == 0){
                if (code == KeyEvent.VK_Z){
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_S){
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 2){
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        gp.ui.titleScreenState = 1;
                    }
                    if(gp.ui.commandNum == 1){
                        gp.gameState = gp.playState;
                        //later
                    }
                    if(gp.ui.commandNum == 2){
                        System.exit(0);
                    }
                }
            }
            else if(gp.ui.titleScreenState == 1){
                if (code == KeyEvent.VK_Z){
                    gp.ui.commandNum--;
                    if(gp.ui.commandNum < 0){
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_S){
                    gp.ui.commandNum++;
                    if(gp.ui.commandNum > 3){
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER){
                    if(gp.ui.commandNum == 0){
                        gp.gameState = gp.playState;
                        System.out.println("Fighter selected");
                    }
                    if(gp.ui.commandNum == 1){
                        gp.gameState = gp.playState;
                        System.out.println("Thief selected");
                    }
                    if(gp.ui.commandNum == 2){
                        gp.gameState = gp.playState;
                        System.out.println("Mage selected");
                    }
                    if(gp.ui.commandNum == 3){
                        gp.ui.titleScreenState = 0;
                    }
                }
            }
        }

        //PLAY STATE----------------------
        if(gp.gameState == gp.playState){
            if (code == KeyEvent.VK_Z){
                upPressed = true;
            }
            if (code == KeyEvent.VK_S){
                downPressed = true;
            }
            if (code == KeyEvent.VK_Q){
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D){
                rightPressed = true;
            }

            if (code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER){
                enterPressed = true;
            }
            if (code == KeyEvent.VK_C){

                System.out.println("x :" + gp.player.worldX/gp.tileSize);
                System.out.println("y :" + gp.player.worldY/gp.tileSize);
            }



            //DEBUG
            if (code == KeyEvent.VK_T){
                if(debugMode == false){
                    debugMode = true;
                }else if (debugMode == true){
                    debugMode = false;
                }

            }
        }

        //PAUSE STATE
        else if (gp.gameState == gp.pauseState){
            if (code == KeyEvent.VK_P){
                gp.gameState = gp.playState;
            }
        }

        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState){
            if (code == KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_Z){
            upPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_Q){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }

        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = false;
        }
    }
}
