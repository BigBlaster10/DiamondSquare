package org.trompgames.isometric;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputAdapter;

import org.trompgames.diamondsquare.Location;

public class MouseListener extends MouseInputAdapter {

	private Location lastLocation;
	private IsoPanel panel;
	
	public MouseListener(IsoPanel panel){
		this.panel = panel;
		new MouseWheelList(panel);
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
        lastLocation = new Location(event.getX(), event.getY());
        //.out.println("Pressed");
	}
	
	@Override
	public void mouseDragged(MouseEvent event) {
		//System.out.println("PDragged");
		
		Location offset = new Location(event.getX(), event.getY()).minus(lastLocation);
		panel.setMapOffset(panel.getMapOffset().add(offset));
		
        lastLocation = new Location(event.getX(), event.getY());

	}
	
	public static class MouseWheelList implements MouseWheelListener{
		private IsoPanel panel;

		public MouseWheelList(IsoPanel panel){
			this.panel = panel;		
			panel.addMouseWheelListener(this);
		}
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent event) {
			if(panel.getMapScale() + -event.getWheelRotation() <= 0){
				return;
			}
			panel.setMapScale(panel.getMapScale() + -event.getWheelRotation());
		}
		
	}
	
	@Override
	public void mouseMoved(MouseEvent event) {
		//System.out.println("moved");
	}

	@Override
    public void mouseReleased(MouseEvent event) {
		//System.out.println("Released");
	}
}
