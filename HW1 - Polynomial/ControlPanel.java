import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import functionality.Polynomial;
import functionality.Term;
//need to correct the old-aged types like Saturday;

public class ControlPanel extends JPanel implements MouseListener{

	private int posY = 50;
	private ArrayList<JButton> generateButtons = new ArrayList<JButton>();
	private ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	private DisplayPanel disp;


	private JButton btn = new JButton("Create ");
	
	//constructor
	public ControlPanel(DisplayPanel d)
	{
		//reference to the current DisplayPanel object
		disp = d;
		
		//set properties for the panel
		this.setBackground(Color.white);
		this.setPreferredSize(new Dimension(500, posY));
		this.setOpaque(true);
		//use absolute positioning
		this.setLayout(null);
		
		
		JLabel lbl = new JLabel("Create a new Polynomial: ");
		lbl.setBounds(5, 15, 150, 25);
		this.add(lbl);


		btn.setBounds(155, 15, 100, 25);
		btn.setVisible(true);
		this.add(btn);
		btn.addMouseListener(this);


		
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);


		int vertical = 50;
		for(int i = 0; i < textFields.size(); i++)
		{
			textFields.get(i).setBounds(5, vertical, 350, 25);

			generateButtons.get(i).setBounds(380, vertical, 100, 25);
			vertical += 30;
			this.add(textFields.get(i));
			this.add(generateButtons.get(i));

		}


	}

	public void mouseClicked(MouseEvent e)
	{

		if(e.getSource() == btn)
		{
			this.textFields.add(new JTextField());
			int a = textFields.size();
			this.generateButtons.add(new JButton("Generate"));
			generateButtons.get(generateButtons.size() - 1).addMouseListener(this);
			posY += 30;
			this.setPreferredSize(new Dimension(500, posY));

			this.revalidate();
			this.repaint();
		}
		else
		{

			String line = new String();
			int who = generateButtons.indexOf(e.getSource());
			line = textFields.get(who).getText();
			line = line.toLowerCase();
			line = line.replace(" ", "");
			line = line.replaceAll("\\+", "~+");
			line = line.replaceAll("\\-", "~-");


			String[] buff = line.split("~");
			int exp = -1, coeff = -1;
			Polynomial p = new Polynomial();
			
			if(line.equals("clear"))
			{
				disp.collection.clearCollection();
				disp.revalidate();
				disp.repaint();
				for(int i = 0; i < textFields.size(); i++)
					textFields.get(i).setText("");
				
				return;
			}
			if(line.equals(""))
			{
				textFields.get(who).setText("Please write your input!");
				return;
			}


			for(int i = 0; i < buff.length; i++)
			{
				if(buff[i].equals("x") || buff[i].equals("+x")) //if input is only x
				{
					coeff = 1;
					exp = 1;
					if(p.termExists(exp) == null)
					{
						p.addTerm(new Term(exp, coeff));
					}
					else
					{
						p.termExists(exp).setCoefficient(p.termExists(exp).getCoefficient() + coeff);
					}

				}
				else
				{
					int a1 = buff[i].indexOf('x');

					if(a1 == buff[i].length() - 1)//if input like nX
					{
						if(buff[i].substring(0, a1).startsWith("+"))
						{
							if(isNumber(buff[i].substring(1, a1)))
							{
								coeff = Integer.valueOf((buff[i].substring(1, a1)));
								exp = 1;
							}
							if(buff[i].substring(1, a1).equals(""))
							{
								coeff = 1;
							}
						}
						else
						{
							if(isNumber(buff[i].substring(0, a1)))
							{
								coeff = Integer.valueOf((buff[i].substring(0, a1)));
								exp = 1;
							}
						}
					}
					else 
						if(a1 == -1) //if input is constant
						{
							if(buff[i].startsWith("+"))
							{
								if(isNumber(buff[i].substring(1)))
								{
									coeff = Integer.valueOf((buff[i].substring(1)));
									exp = 0;
								}
							}
							else
							{
								if(isNumber(buff[i]))
								{
									coeff = Integer.valueOf((buff[i]));
									exp = 0;
								}
							}
						}
						else

							if(a1 == 0) // if input like x^n
							{
								if(buff[i].substring(a1 + 1, buff[i].length() - 1).startsWith("^"))
								{
									if(isNumber(buff[i].substring(a1 + 2, buff[i].length())))
									{
										exp = Integer.valueOf((buff[i].substring(a1 + 2, buff[i].length())));
										coeff = 1;
									}
								}
							}
							else
							{
								if(buff[i].substring(0, a1).endsWith("*")) // if input like m*X^n
								{
									if(buff[i].substring(0, a1 - 1).startsWith("+"))
									{
										if(isNumber(buff[i].substring(1, a1 - 1)))
										{
											coeff = Integer.valueOf((buff[i].substring(1, a1 - 1))); 
										}

										if(buff[i].substring(1, a1 - 1).equals(""))
										{
											coeff = 1;
										}
									}
									else
									{
										if(isNumber(buff[i].substring(0, a1 - 1)))
										{
											coeff = Integer.valueOf(((buff[i].substring(0, a1 - 1)))); 
										}
									}
								}
								else
								{
									if(buff[i].substring(0, a1).startsWith("+")) //if input like mX^n
									{
										if(isNumber(buff[i].substring(1, a1)))
										{
											coeff = Integer.valueOf((buff[i].substring(1, a1))); 
										}
										if(buff[i].substring(1, a1).equals(""))
										{
											coeff = 1;
										}
									}
									else
									{
										if(isNumber(buff[i].substring(0, a1)))
										{
											coeff = Integer.valueOf((buff[i].substring(0, a1))); 
										}
									}
								}

								if(buff[i].substring(a1 + 1, buff[i].length() - 1).startsWith("^"))
								{
									if(isNumber(buff[i].substring(a1 + 2, buff[i].length())))
									{
										exp = Integer.valueOf((buff[i].substring(a1 + 2, buff[i].length()))); 
									}
								}
							}




					if(p.termExists(exp) == null && exp != -1)
					{
						p.addTerm(new Term(exp, coeff));
					}
					else
					{
						if(exp != -1)
							p.termExists(exp).setCoefficient(p.termExists(exp).getCoefficient() + coeff);
					}

				}
			}

			
			p.completePolynomial();
			p.arrangePolynomial();
			if(disp.collection.getSize() > who)
			{
				disp.collection.updatePolynomial(who, p);
			}
			else
			{
				disp.collection.addPolynomial(p);
			}

			disp.revalidate();
			disp.repaint();

		}

	}


	private boolean isNumber(String s)
	{
		char[] temp;
		int z = 0;
		if(s.equals(""))
		{
			return false;
		}
		if(s.startsWith("-"))
		{
			s = s.substring(1);
			temp = s.toCharArray();
		}
		else temp = s.toCharArray();


		for(int i = 0; i < temp.length; i++)
		{
			if(temp[i] >= '0' && temp[i] <= '9') z++;
			else return false;
		}
		if(z != temp.length)
			return false;
		else
			return true;
	}
	
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
}
