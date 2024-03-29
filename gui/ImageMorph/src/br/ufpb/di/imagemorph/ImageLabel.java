/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ufpb.di.imagemorph;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Thiago
 */
public class ImageLabel extends JLabel 
        implements MouseMotionListener, MouseListener {

    private static int BIND_NONE  = 0;
    private static  int BIND_START = 1;
    private static int BIND_END   = 2;

    public static final Icon NO_IMAGE = new ImageIcon(
            ImageLabel.class.getResource
                ("/br/ufpb/di/imagemorph/icon/no-image.png"));

    private List<WarpingLine> lines = new ArrayList<WarpingLine> ();

    private WarpingLine theChosenOne;
    private Point lastKnownMousePosition;

    private int mouseBind;

    private ImageLabel theOtherGuy;

    public ImageLabel(Icon myImage, ImageLabel another) {
        super();
        if (myImage != null)
            setIcon(myImage);
        else
            setIcon(NO_IMAGE);

        //lines.add(new WarpingLine());
        //lines.get(0).setLine(10, 10, 10, 10);
        addMouseMotionListener(this);
        addMouseListener(this);

        lastKnownMousePosition = new Point(0,0);

        if (another != null) {
            this.theOtherGuy = another;
            another.theOtherGuy = this;
        }

    }

    public ImageLabel (ImageLabel another) {
        this(NO_IMAGE, another);
    }

    @Override
    protected void paintComponent(Graphics g) {
        getIcon().paintIcon(this, g, 0, 0);

        verifySelectedLine();

        if (theOtherGuy != null && theChosenOne != null)
            theOtherGuy.repaint();
        //System.out.println("Is bound? " + isBound());

        for (WarpingLine i : lines) {
            i.paintIcon(this, g, 0, 0);
        }

    }

    public void clearLines () {
        this.lines.clear();
        theOtherGuy.lines.clear();
        repaint();
    }

    public void addLinesOnBorders () {
        addBorderLines(this, theOtherGuy);
    }

    public Line2D[] getLines () {
        Line2D result[] = new Line2D[lines.size()];

        for (int i = 0; i < result.length; ++i) {
            result[i] = lines.get(i).asLine2D();
        }

        return result;
    }

    private static void addBorderLines (ImageLabel guy, ImageLabel dude) {

        WarpingLine north1 = new WarpingLine();
        WarpingLine north2 = new WarpingLine(north1);
        north1.setLineStart(0, 0);
        north2.setLineStart(0, 0);
        north1.setLineEnd  (guy .getIcon().getIconWidth(), 0);
        north2.setLineEnd  (dude.getIcon().getIconWidth(), 0);
        guy .lines.add(north1);
        dude.lines.add(north2);

        WarpingLine east1 = new WarpingLine();
        WarpingLine east2 = new WarpingLine(east1);
        east1.setLineStart(guy .getIcon().getIconWidth(), 0);
        east2.setLineStart(dude.getIcon().getIconWidth(), 0);
        east1.setLineEnd  (guy .getIcon().getIconWidth(), guy .getIcon().getIconHeight());
        east2.setLineEnd  (dude.getIcon().getIconWidth(), dude.getIcon().getIconHeight());
        guy .lines.add(east1);
        dude.lines.add(east2);

        WarpingLine south1 = new WarpingLine();
        WarpingLine south2 = new WarpingLine(south1);
        south1.setLineStart(guy .getIcon().getIconWidth(), guy .getIcon().getIconHeight());
        south2.setLineStart(dude.getIcon().getIconWidth(), dude.getIcon().getIconHeight());
        south1.setLineEnd  (0, guy .getIcon().getIconHeight());
        south2.setLineEnd  (0, dude.getIcon().getIconHeight());
        guy .lines.add(south1);
        dude.lines.add(south2);

        WarpingLine west1 = new WarpingLine();
        WarpingLine west2 = new WarpingLine(west1);
        west1.setLineStart(0, guy .getIcon().getIconHeight());
        west2.setLineStart(0, dude.getIcon().getIconHeight());
        west1.setLineEnd  (0, 0);
        west2.setLineEnd  (0, 0);
        guy .lines.add(west1);
        dude.lines.add(west2);

        guy.repaint();

    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
        if (icon != null) {
            Dimension size = new Dimension(getIcon().getIconWidth(), getIcon().getIconHeight());
            setMinimumSize(size);
            setMaximumSize(size);
            setPreferredSize(size);
            setSize(size);
        }
    }

    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseMoved(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

//        lines.get(0).setLineEnd(x, y);

//        System.out.println(lines.get(0));

        lastKnownMousePosition = e.getPoint();

        moveChosenOne(x, y);

        repaint();
        
    }

    private void bindStart() {
        if (theChosenOne != null)
            mouseBind = BIND_START;
    }

    private void bindEnd() {
        if (theChosenOne != null)
            mouseBind = BIND_END;
    }

    private void makeNewLine (Point p) {
        if (theChosenOne == null && pointBelongsToImage(p.x, p.y)) {

            WarpingLine tmp = new WarpingLine();
            WarpingLine brother = new WarpingLine(tmp);

            tmp.bringYourBrotherWithYou();

            lines.add(tmp);

            if (theOtherGuy != null)
                theOtherGuy.lines.add(brother);

            tmp.setLine(new Line2D.Float(p, p));

            verifySelectedLine();

//            if (theChosenOne != null)
//                theChosenOne.bringYourBrotherWithYou();

            
        }
    }

    private void sendToHell () {
        if (theChosenOne != null) {
            lines.remove(theChosenOne);
            theOtherGuy.lines.remove(theChosenOne.whoIsYourBrother());
        }
    }

    private final boolean pointBelongsToImage (int x, int y) {
        //atencao!! O x, y do swing e diferente do x, y da ImageWrapper
        return x >=0 &&
               x < getIcon().getIconWidth() &&
               y >=0 &&
               y < getIcon().getIconHeight();
    }

    private void moveChosenOne(int x, int y) {

        if (pointBelongsToImage(x, y) && theChosenOne != null && isBound()) {
            if (mouseBind == BIND_START)
                theChosenOne.setLineStart(x, y);
            else if (mouseBind == BIND_END)
                theChosenOne.setLineEnd(x, y);
        }
        else {
            unbind();
        }
    }

    private void unbind() {
        mouseBind = BIND_NONE;
        if (theChosenOne != null) {
            theChosenOne.leaveYourBrotherAlone();
        }
    }

    private boolean isBound () {
        return mouseBind != BIND_NONE;
    }

    private void verifySelectedLine() {
        
        WarpingLine theNewChosenOne = null;

        while(lines.remove(null));

        for (WarpingLine i : lines) {

            i.setSelected(false);

            if (theNewChosenOne == null) {

                if (i.myPosition.ptSegDist(lastKnownMousePosition) < 10) {
                    theNewChosenOne = i;
                }
            }

            else if (
                i.myPosition.ptSegDist(lastKnownMousePosition) < theNewChosenOne.myPosition.ptSegDist(lastKnownMousePosition)) {

                theNewChosenOne = i;
            }
        }

        if (theNewChosenOne != null)
            theNewChosenOne.setSelected(true);

        this.theChosenOne = theNewChosenOne;

        if (theOtherGuy != null)
            theOtherGuy.repaint();

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

        if (!isBound())
            makeNewLine(e.getPoint());

        if (isBound())
            unbind();
        else if (e.getButton() == MouseEvent.BUTTON1)
            bindStart();
        else if (e.getButton() == MouseEvent.BUTTON2)
            sendToHell();
        else if (e.getButton() == MouseEvent.BUTTON3)
            bindEnd();
        else
            unbind();

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        unbind();
    }




}
