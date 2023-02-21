'''
Keeps track of the board state and determains valid moves

Keeps a move log for the game
'''

import Moves

class GameState():
    # constructor for the class GameState
    def __init__(self):
        # game state
        self.current_player_white = True
        self.move_log = []

        # initialized board so white is on bottom and black pieces are on top
        # "--" indicates an open space
        self.board = [
            ["black_rook", "black_knight", "black_bishop", "black_queen", "black_king", "black_bishop", "black_knight", "black_rook"],
            ["black_pawn", "black_pawn", "black_pawn", "black_pawn", "black_pawn", "black_pawn", "black_pawn", "black_pawn"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["--", "--", "--", "--", "--", "--", "--", "--"],
            ["white_pawn", "white_pawn", "white_pawn", "white_pawn", "white_pawn", "white_pawn", "white_pawn", "white_pawn"],
            ["white_rook", "white_knight", "white_bishop", "white_queen", "white_king", "white_bishop", "white_knight", "white_rook"]]

    '''
    makes a move on the game board
    '''
    def make_move(self, move):
        self.board[move.start_row][move.start_col] = "--"
        self.board[move.end_row][move.end_col] = move.starting_piece
        self.move_log.append(move)
        self.current_player_white = not self.current_player_white

    '''
    undo last move made
    '''
    def undo_move(self):
        if len(self.move_log) != 0: # if at least one move has been made
            move = self.move_log.pop()
            self.board[move.start_row][move.start_col] = move.starting_piece
            self.board[move.end_row][move.end_col] = move.ending_piece
            self.current_player_white = not self.current_player_white

    '''
    identifies all moves while considering checks
    '''
    def get_valid_moves(self):
        return self.get_all_possible_moves() # ! place holder while implementing all possible moves

    '''
    identifies all moves without considering checks
    '''
    def get_all_possible_moves(self):
        possible_moves = []

        for row in range(len(self.board)):
            for col in range(len(self.board[row])):
                if ("white" in self.board[row][col] and self.current_player_white) or ("black" in self.board[row][col] and not self.current_player_white): # if piece on tile belongs to the current player
                    if "pawn" in self.board[row][col]: # pawn moves
                        self.get_pawn_moves(row, col, possible_moves)
                    if "rook" in self.board[row][col]: # rook moves
                        self.get_rook_moves(row, col, possible_moves)
                    if "knight" in self.board[row][col]: # knight moves
                        self.get_knight_moves(row, col, possible_moves)
                    if "bishop" in self.board[row][col]: # bishop moves
                        self.get_bishop_moves(row, col, possible_moves)
                    if "queen" in self.board[row][col]: # queen moves
                        self.get_queen_moves(row, col, possible_moves)
                    if "king" in self.board[row][col]: # king moves
                        self.get_king_moves(row, col, possible_moves)
                    
        return possible_moves

    '''
    get all pawn moves for the pawn located at specified tile and add moves to the list of possible moves
    '''
    def get_pawn_moves(self, row, col, possible_moves): 
        if self.current_player_white: # if it is whites turn
            if self.board[row - 1][col] == "--": # if tile in-front of pawn is open
                possible_moves.append(Moves.Moves((row, col), (row - 1, col), self.board))

                if row == 6 and self.board[row - 2][col] == "--": # if pawn hasn't been moved yet - ability to move two tiles
                    possible_moves.append(Moves.Moves((row, col), (row - 2, col), self.board))
        else: # TODO else blacks turn
            if self.board[row + 1][col] == "--": # if tile in-front of pawn is open
                possible_moves.append(Moves.Moves((row, col), (row + 1, col), self.board))

                if row == 1 and self.board[row + 2][col] == "--": # if pawn hasn't been moved yet - ability to move two tiles
                    possible_moves.append(Moves.Moves((row, col), (row + 2, col), self.board))

    '''
    get all rook moves for the pawn located at specified tile and add moves to the list of possible moves
    '''
    def get_rook_moves(self, row, col, moves): # TODO 
        pass

    '''
    get all knight moves for the pawn located at specified tile and add moves to the list of possible moves
    '''
    def get_knight_moves(self, row, col, moves): # TODO 
        pass

    '''
    get all bishop moves for the pawn located at specified tile and add moves to the list of possible moves
    '''
    def get_bishop_moves(self, row, col, moves): # TODO 
        pass

    '''
    get all queen moves for the pawn located at specified tile and add moves to the list of possible moves
    '''
    def get_queen_moves(self, row, col, moves): # TODO 
        pass

    '''
    get all king moves for the pawn located at specified tile and add moves to the list of possible moves
    '''
    def get_king_moves(self, row, col, moves): # TODO 
        pass