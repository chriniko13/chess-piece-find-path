package com.chriniko.chess.view;

import com.chriniko.chess.infra.board.Board;
import com.chriniko.chess.infra.board.Position;
import com.chriniko.chess.infra.path.Path;
import com.chriniko.chess.infra.piece.Knight;
import com.chriniko.chess.infra.piece.Pawn;
import com.chriniko.chess.infra.piece.Piece;
import com.chriniko.chess.infra.piece.PieceColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.stream.Collectors;

public class ChessBoardUI {

	private static final String COLS = "ABCDEFGH";
	private final Board board = new Board();
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private final JLabel message = new JLabel("Chess Path Calculator is ready!");

	private JButton[][] chessBoardSquares = new JButton[8][8];
	private JPanel chessBoard;

	private JRadioButton knightPieceType;
	private JRadioButton pawnPieceType;

	private JRadioButton dfsApproach;
	private JRadioButton bfsApproach;

	private boolean isStartSelected = false;
	private boolean isEndSelected = false;

	// Note: start is colored blue, end is colored red.

	private JButton lastButtonMarkedRed = null;
	private Color colorOfLastButtonMarkedRed = null;
	private Position startPosition = null;

	private JButton lastButtonMarkedBlue = null;
	private Color colorOfLastButtonMarkedBlue = null;
	private Position endPosition = null;

	ChessBoardUI() {
		initializeGui();
	}

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

		Runnable r = () -> {
			ChessBoardUI cb = new ChessBoardUI();

			JFrame f = new JFrame("Chess Path Calculator - nick.christidis@yahoo.com");
			f.add(cb.getGui());
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setLocationByPlatform(true);

			// ensures the frame is the minimum size it needs to be
			// in order display the components within it
			f.pack();
			// ensures the minimum size is enforced.
			f.setMinimumSize(f.getSize());
			f.setVisible(true);

		};
		SwingUtilities.invokeLater(r);
	}

	public final void initializeGui() {
		// set up the main GUI
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));

		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);

		ButtonGroup startEndToggleButtonsGroup = new ButtonGroup();

		JToggleButton startToggleButton = new JToggleButton("Start");
		JToggleButton endToggleButton = new JToggleButton("End");

		startEndToggleButtonsGroup.add(startToggleButton);
		startEndToggleButtonsGroup.add(endToggleButton);

		startToggleButton.addActionListener(e -> {
			isStartSelected = true;
			isEndSelected = false;
		});
		endToggleButton.addActionListener(e -> {
			isEndSelected = true;
			isStartSelected = false;
		});

		tools.add(startToggleButton);
		tools.add(endToggleButton);

		JButton calculatePathButton = new JButton("Calculate");
		calculatePathButton.addActionListener(new ActionListener() {

			@Override public void actionPerformed(ActionEvent e) {

				if (startPosition == null || endPosition == null) {
					JOptionPane.showMessageDialog(gui, "Please select start and/or end position, and try again.",
							"Please select start and/or end position!", JOptionPane.WARNING_MESSAGE);
					return;
				}

				final Piece piece;

				if (knightPieceType.isSelected()) {
					piece = new Knight(PieceColor.BLACK, "knight-black-piece");
				} else if (pawnPieceType.isSelected()) {
					piece = new Pawn(PieceColor.BLACK, "pawn-black-piece");
				} else {
					throw new IllegalStateException();
				}

				board.setPiece(piece, startPosition);

				final Set<Path> calculatedPaths;
				if (dfsApproach.isSelected()) {
					calculatedPaths = board.calculatePathDFS(startPosition, endPosition);
				} else {
					calculatedPaths = board.calculatePathBFS(startPosition, endPosition);
				}

				// Note: display an information message.
				String pathsStringified = calculatedPaths.stream().map(Path::getPathStringified).collect(Collectors.joining("\n"));
				if (pathsStringified.isEmpty()) {
					pathsStringified = "No paths found!";
				}

				JOptionPane.showMessageDialog(gui, "The calculated paths are the following: \n" + pathsStringified);
			}
		});

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(e -> {

			if (lastButtonMarkedBlue != null) {
				lastButtonMarkedBlue.setBackground(colorOfLastButtonMarkedBlue);
				lastButtonMarkedBlue = null;
				colorOfLastButtonMarkedBlue = null;
				startPosition = null;
			}

			if (lastButtonMarkedRed != null) {
				lastButtonMarkedRed.setBackground(colorOfLastButtonMarkedRed);
				lastButtonMarkedRed = null;
				colorOfLastButtonMarkedRed = null;
				endPosition = null;
			}

			knightPieceType.setSelected(true);
			dfsApproach.setSelected(true);
		});

		tools.add(calculatePathButton);
		tools.addSeparator();

		tools.add(clearButton);
		tools.addSeparator();

		knightPieceType = new JRadioButton("Knight", true);
		pawnPieceType = new JRadioButton("Pawn", false);

		ButtonGroup pieceTypesGroup = new ButtonGroup();
		pieceTypesGroup.add(knightPieceType);
		pieceTypesGroup.add(pawnPieceType);

		tools.add(knightPieceType);
		tools.add(pawnPieceType);

		tools.addSeparator();

		ButtonGroup calculateApproachGroup = new ButtonGroup();
		dfsApproach = new JRadioButton("DFS", true);
		bfsApproach = new JRadioButton("BFS", true);
		calculateApproachGroup.add(dfsApproach);
		calculateApproachGroup.add(bfsApproach);
		tools.add(dfsApproach);
		tools.add(bfsApproach);

		tools.addSeparator();
		tools.add(message);

		gui.add(new JLabel("?"), BorderLayout.LINE_START);

		chessBoard = new JPanel(new GridLayout(0, 9));
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		gui.add(chessBoard);

		// create the chess board squares
		Insets buttonMargin = new Insets(0, 0, 0, 0);

		for (int ii = 0; ii < chessBoardSquares.length; ii++) {
			for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {

				JButton b = new JButton();
				b.setMargin(buttonMargin);
				// our chess pieces are 64x64 px in size, so we'll
				// 'fill this in' using a transparent icon..
				ImageIcon icon = new ImageIcon(
						new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);

				if ((jj % 2 == 1 && ii % 2 == 1)
						//) {
						|| (jj % 2 == 0 && ii % 2 == 0)) {
					b.setBackground(Color.WHITE);
				} else {
					b.setBackground(Color.BLACK);
				}
				chessBoardSquares[jj][ii] = b;

				// Note: use heap in order to access inside closure.
				int[] xHeap = new int[] { ii };
				int[] yHeap = new int[] { jj };
				b.addActionListener(new ActionListener() {

					private final int x = xHeap[0];
					private final int y = yHeap[0];

					@Override public void actionPerformed(ActionEvent e) {

						Position position = Position.create(x, y).orElseThrow(IllegalStateException::new);
						System.out.println("You clicked position: " + x + ", " + y + " --- " + position.getName());
						JButton button = (JButton) e.getSource();

						if (isStartSelected) {

							if (lastButtonMarkedBlue != null) {
								lastButtonMarkedBlue.setBackground(colorOfLastButtonMarkedBlue);
								lastButtonMarkedBlue = null;
								colorOfLastButtonMarkedBlue = null;
								startPosition = null;
							}

							lastButtonMarkedBlue = button;
							colorOfLastButtonMarkedBlue = button.getBackground();

							button.setBackground(Color.BLUE);
							startPosition = position;
						}

						if (isEndSelected) {

							if (lastButtonMarkedRed != null) {
								lastButtonMarkedRed.setBackground(colorOfLastButtonMarkedRed);
								lastButtonMarkedRed = null;
								colorOfLastButtonMarkedRed = null;
								endPosition = null;
							}

							lastButtonMarkedRed = button;
							colorOfLastButtonMarkedRed = button.getBackground();

							button.setBackground(Color.RED);
							endPosition = position;
						}

					}
				});
			}
		}

		//fill the chess board
		chessBoard.add(new JLabel(""));

		// fill the top row
		for (int ii = 0; ii < 8; ii++) {
			chessBoard.add(
					new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
		}

		// fill the black non-pawn piece row
		for (int ii = 0; ii < 8; ii++) {
			for (int jj = 0; jj < 8; jj++) {
				if (jj == 0) {
					chessBoard.add(new JLabel("" + (8 - ii), SwingConstants.CENTER));
				}
				chessBoard.add(chessBoardSquares[jj][ii]);
			}
		}
	}

	public final JComponent getGui() {
		return gui;
	}

}
