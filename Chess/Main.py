'''
Driver file

Loads up a chess board to display for the user, and handles user input

Created By: Josh Johnson
'''

# python libraries
import random # implements pseudo-random number generators for various distributions
import pygame # gui for python game
from os import path # used to get absolute path for the project

# project classes
import GameState
import Moves

# game board settings
BOARD_WIDTH = 400 # game board width
BOARD_HEIGHT = 400 # game board height
DIMENSION = 8 # 8x8 board
TILE_SIZE = BOARD_WIDTH // DIMENSION # size of a square (tile) on the gui
PIECE_IMAGES = {} # holds the locations of all the chess piece images
MAX_FPS = 15 # for animations

# window settings
WINDOW_WIDTH = BOARD_WIDTH + 300
WINDOW_HEIGHT = BOARD_HEIGHT + 200
GAP = 25 # spacing the board is from the edge of the window

# game log settings
log_frame_width = WINDOW_WIDTH - BOARD_WIDTH - GAP - (GAP * 2)
log_frame_height = WINDOW_HEIGHT - (GAP * 2)
log_frame_starting_x_coordinate = BOARD_WIDTH + GAP + GAP
log_frame_starting_y_coordinate = GAP
game_log = []

# heading panel settings
heading_width = BOARD_WIDTH
heading_height = WINDOW_HEIGHT - BOARD_HEIGHT - (GAP * 3)
heading_starting_x_coordinate = GAP
heading_starting_y_coordinate = GAP

'''
global dictionary of images
'''
def load_images(set):
    base_path = path.dirname(__file__) # finds absolute path for the project

    # determines the piece set
    piece_set = ""
    if set == 1:
        piece_set = "Set1"
    elif set == 2:
        piece_set = "Set2"
    else:
        piece_set = "Set3"

    # loads the piece set
    pieces = ["black_rook", "black_knight", "black_bishop", "black_queen", "black_king", "black_bishop", "black_knight", "black_rook", "black_pawn",
                "white_rook", "white_knight", "white_bishop", "white_queen", "white_king", "white_bishop", "white_knight", "white_rook", "white_pawn"]
    for piece in pieces:
        PIECE_IMAGES[piece] = pygame.transform.scale(pygame.image.load(base_path + "/Game_Images/Piece_Sets/" + piece_set + "/" + piece + ".png"), (TILE_SIZE, TILE_SIZE))

'''
main function
'''
def main():
    # * initialize pygame
    pygame.init()
    screen = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
    clock = pygame.time.Clock()
    pygame.display.set_caption("Lets Play Chess!")
    screen.fill(pygame.Color("light grey"))

    # * prepare game on load up of program
    # heading above game board
    pygame.draw.rect(screen, "white", pygame.Rect(heading_starting_x_coordinate, heading_starting_y_coordinate, heading_width, heading_height))
    title_font = pygame.font.SysFont('monospace', 32, 'bold')
    title_label = title_font.render("The Game of Chess", True, 'black')
    title_rect = title_label.get_rect(center=(heading_width / 2 + GAP, GAP + 30))
    screen.blit(title_label, title_rect)

    # prepare game log panel
    pygame.draw.rect(screen, "white", pygame.Rect(log_frame_starting_x_coordinate, log_frame_starting_y_coordinate, log_frame_width, log_frame_height))
        
    # game board
    game_state = GameState.GameState()
    load_images(1) # TODO change parameter for each set chosen; 1, 2, or 3

    tile_selected = () # keeps track of the last tile clicked by the user
    player_clickes = [] # keeps track of a plyaer clicks; two tuples: [(x1,y1), (x2,y2)]

    running = True

    # * actions to perform for an active game
    while running:
        for e in pygame.event.get(): # handles triggered events by user
            if e.type == pygame.QUIT: # quit application
                running = False
            elif e.type == pygame.MOUSEBUTTONDOWN: # else if mouse has clicked and is holding the button down
                location = pygame.mouse.get_pos() # (x, y) location of the mouse; x value at index 0; y value at index 1                
                col = (location[0] - GAP) // TILE_SIZE 
                row = (location[1] - (WINDOW_HEIGHT - BOARD_HEIGHT - GAP)) // TILE_SIZE
                                
                if col >= 0 and col <= 7 and row >= 0 and row <= 7: # if clicking on the chess board
                    if tile_selected == (row, col): # if user clicked same tile twice in a row
                        tile_selected == () # deselect
                        player_clickes == [] # clear player clicks
                    else: # else; 2 different tiles clicked in order
                        tile_selected = (row, col)
                        player_clickes.append(tile_selected)
                    
                    if len(player_clickes) == 2: # if second tile was clicked that was different than the first
                        move = Moves.Moves(player_clickes[0], player_clickes[1], game_state.board)
                        print(move.get_chess_notation()) # ! used for testing purposes
                        game_state.make_move(move)
                        game_log.append(move.get_chess_notation())

                        # resets user input clicks
                        tile_selected = () 
                        player_clickes = []

        
        display_game_log(screen)
        draw_game_state(screen, game_state) 
        clock.tick(MAX_FPS)
        pygame.display.flip()

''' 
creates all graphics of the game 
'''
def draw_game_state(screen, game_state):
    draw_board(screen) 
    draw_pieces(screen, game_state.board) 

''' 
draws the squares on the board; top left square is always light
'''
def draw_board(screen):
    # chess sets
    colors = [pygame.Color("white"), pygame.Color("grey")] # set 1
    # TODO set 2
    # TODO set 3

    # colors the tiles
    for row in range(DIMENSION):
        for col in range(DIMENSION):
            color = colors[((row + col) % 2)]
            pygame.draw.rect(screen, color, pygame.Rect((col * TILE_SIZE) + 25, (row * TILE_SIZE) + WINDOW_HEIGHT - BOARD_HEIGHT - 25, TILE_SIZE, TILE_SIZE))

'''
draw pieces on top of the board
'''
def draw_pieces(screen, board):
    for row in range(DIMENSION):
        for col in range(DIMENSION):
            piece = board[row][col]

            # if not an empty tile; has a piece on the tile 
            if piece != "--":
                screen.blit(PIECE_IMAGES[piece], pygame.Rect((col * TILE_SIZE) + 25, (row * TILE_SIZE) + WINDOW_HEIGHT - BOARD_HEIGHT - 25, TILE_SIZE, TILE_SIZE))

'''
display game log in panel 
'''
def display_game_log(screen): # TODO make scrollable game log
    pass


# convension for calling the main function; useful for running as a script
if __name__ == "__main__":
    main()