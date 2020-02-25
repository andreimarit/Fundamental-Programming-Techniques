import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import functionality.Operation;
//like Saturday
public class OperationsPanel extends JPanel{

	private DisplayPanel display;
	private JLabel result;
	private JFrame inputFrame;
	private JPanel inputPanel;
	private JTextField inputValue;
	private JButton inputOKCompute;

	public OperationsPanel(DisplayPanel dp)
	{
		display = dp;
		inputValue = new JTextField();
		inputOKCompute = new JButton("OK");


		this.setLayout(new GridLayout(2, 1));
		this.setPreferredSize(new Dimension(100, 100));
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 8));

		JButton add = new JButton("+");
		JButton subtract = new JButton("-");
		JButton multiply = new JButton("*");
		JButton divide = new JButton("/");
		JButton derivate = new JButton("<html>f'(x)</html>");
		JButton integrate = new JButton("<html>&int; f(x)</html>");
		JButton compute = new JButton("f(n)=");

		result = new JLabel();
		result.setPreferredSize(new Dimension(45, 100));

		buttons.add(add);
		buttons.add(subtract);
		buttons.add(multiply);
		buttons.add(divide);
		buttons.add(derivate);
		buttons.add(integrate);
		buttons.add(compute);

		this.add(result);
		this.add(buttons);

		add.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				Operation op = new Operation(display.collection);
				result.setText("<html>Summation: " + op.addition().printPolynomial()+ "</html>");
			}
		});
		subtract.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				Operation op = new Operation(display.collection);
				result.setText("<html>Subtraction: " + op.subtraction().printPolynomial() + "</html>");
			}
		});
		multiply.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				Operation op = new Operation(display.collection);
				result.setText("<html>Multiplication: " + op.multiplication().printPolynomial() + "</html>");
			}
		});
		divide.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				Operation op = new Operation(display.collection);
				if(op.division() != null)
				{
					result.setText("<html>Q: " + op.division().getPolynomial(0).printPolynomial() + "<br/> R: " + op.division().getPolynomial(1).printPolynomial() + "</html>");
				}
				else
				{
					result.setText("Could not make the division");
				}
			}
		});

		derivate.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				display.derivates.clearCollection();
				for(int i = 0; i < display.collection.getSize(); i++)
				{
					Operation op = new Operation(display.collection.getPolynomial(i));
					display.derivates.addPolynomial(op.derivative());
				}

				display.revalidate();
				display.repaint();
			}
		});
		
		integrate.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				display.integrals.clearCollection();
				for(int i = 0; i < display.collection.getSize(); i++)
				{
					Operation op = new Operation(display.collection.getPolynomial(i));
					display.integrals.addPolynomial(op.integral());
				}

				display.revalidate();
				display.repaint();
			}
		});

		
		compute.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				inputFrame = new JFrame("Your Input");
				inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				inputValue.setText("");
				
				inputPanel = new JPanel();
				inputPanel.setPreferredSize(new Dimension(300, 100));
				inputPanel.setLayout(new GridLayout(2, 1));
				inputPanel.add(inputValue);
				inputPanel.add(inputOKCompute);
				inputOKCompute.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						if(isNumber(inputValue.getText()))
						{
							display.results.removeAll(display.results);
							for(int i = 0; i < display.collection.getSize(); i++)
							{
								Operation op = new Operation(display.collection.getPolynomial(i));
								display.results.add(op.compute(Integer.valueOf((inputValue.getText()))));
							}
							display.value = inputValue.getText();
						}
						display.revalidate();
						display.repaint();
						inputFrame.dispose();
					}
				});

				inputFrame.setContentPane(inputPanel);
				inputFrame.pack();
				inputFrame.setVisible(true);

			}
		});

	}

	private boolean isNumber(String s)
	{
		s.replaceAll(" ", "");
		char[] temp;
		if(s.startsWith("-"))
		{
			s.substring(1);
			temp = s.toCharArray();
		}
		else
		{
			temp = s.toCharArray();
		}
		if(s.equals(""))
		{
			return false;
		}
		else
		{			
			
			for(int i = 0; i < temp.length; i++)
			{
				if(temp[i] < '0' || temp[i] > '9') return false;
			}
		}
		return true;
	}

}
