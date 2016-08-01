; Author: Kenneth Cason
; www.kennycason.com
; TileDim09 March 22
; Compiled with Purebasic 4.30


If InitMouse() = 0
  MessageRequester("Error", "Failed to Init Mouse!", 0)
  End
EndIf
If InitKeyboard() = 0
  MessageRequester("Error", "Failed to Init Keyboard!", 0)
  End
EndIf

Declare ShiftRowLeft(row.b) 
Declare ShiftRowRight(row.b) 
Declare ShiftColumnUp(column.b)
Declare ShiftColumnDown(column.b)
 
Global Width = 10
Global Height = 10
Global TileDim = 20

Global WINDOW_WIDTH = Width * TileDim + 1
Global WINDOW_HEIGHT = Height *TileDim + 1

Global Title$ = "TileZ"
Global Exit = 0

Global Moves = 0

#MOUSE = 0
#TILE_RED = 1
#TILE_BLUE = 2
#TILE_GREEN = 3
#TILE_YELLOW = 4

#MENU_QUIT = 0
#MENU_RESET = 1
#MENU_SCRAMBLE = 2

Global Dim Tiles.b(Width, Height)
 
 
If InitSprite() = 0
  MessageRequester("Error", "Can't open screen & sprite enviroment!", 0)
  End
EndIf
  
If OpenWindow(0, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, Title$ + " - Press A for Help!", #PB_Window_ScreenCentered)

  
  If OpenWindowedScreen(WindowID(0), 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 0, 0, 0)
      CreateSprite(#MOUSE, 7, 7)
      If StartDrawing(SpriteOutput(#MOUSE))
        Box(0, 0, 7, 7, RGB(0, 0, 0))
        Box(2, 2, 3, 3, RGB(255, 0, 255))
        StopDrawing()
      EndIf
      TransparentSpriteColor(#MOUSE,RGB(255,0,255))
  
      CreateSprite(#TILE_RED, TileDim, TileDim)
      If StartDrawing(SpriteOutput(#TILE_RED))
        Box(0, 0, TileDim, TileDim, RGB(255, 0, 0))
        StopDrawing()
      EndIf
      CreateSprite(#TILE_BLUE, TileDim, TileDim)
      If StartDrawing(SpriteOutput(#TILE_BLUE))
        Box(0, 0, TileDim, TileDim, RGB(0, 0, 155))
        StopDrawing()
      EndIf
      
      CreateSprite(#TILE_GREEN, TileDim, TileDim)
      If StartDrawing(SpriteOutput(#TILE_GREEN))
        Box(0, 0, TileDim, TileDim, RGB(0, 155, 0))
        StopDrawing()
      EndIf
      
      CreateSprite(#TILE_YELLOW, TileDim, TileDim)
      If StartDrawing(SpriteOutput(#TILE_YELLOW))
        Box(0, 0, TileDim, TileDim, RGB(255, 255, 0))
        StopDrawing()
      EndIf
      
      
      Gosub InitTiles
  Else
    MessageRequester("Error", "Can't open windowed screen!", 0)
    End
  EndIf
EndIf



Repeat

  Repeat
    Event = WindowEvent()
    
    Select Event 
   ;   Case #PB_Event_Gadget
      
      Case #PB_Event_CloseWindow
        Exit = 1 
    EndSelect
    
  Until Event = 0

  FlipBuffers() 
  ClearScreen(RGB(0, 0, 0))
  
  For y = 0 To Height - 1
    For x = 0 To Width - 1
      DisplaySprite(Tiles(x,y), x * TileDim, y * TileDim )
    Next
  Next 
  StartDrawing(ScreenOutput())
    For y = 0 To Height
      Line(0, y * TileDim , Width * TileDim, 0, RGB(0,0,0))
    Next 
    For x = 0 To Width
      Line(x * TileDim , 0, 0, Height * TileDim, RGB(0,0,0))
    Next
  StopDrawing()  
  
  ExamineMouse()
    mX = MouseX()
    mY = MouseY()
    If MouseButton(#PB_MouseButton_Left)
      If MouseDeltaX() < -2
        ShiftRowLeft(mY/TileDim)
      ElseIf MouseDeltaX() > 2
        ShiftRowRight(mY/TileDim)
      ElseIf MouseDeltaY() < -2
        ShiftColumnUp(mX/TileDim)
      ElseIf MouseDeltaY() > 2
        ShiftColumnDown(mX/TileDim)
      EndIf
    EndIf 
  DisplayTransparentSprite(#MOUSE, mX - 3, mY - 3)
  StartDrawing(ScreenOutput())
    DrawingMode(#PB_2DDrawing_Transparent)
    DrawText(WINDOW_WIDTH - 42,WINDOW_HEIGHT - 18,Str(Moves),RGB(0,0,0))
  StopDrawing()
  
  
  ExamineKeyboard()
  If KeyboardPushed(#PB_Key_Q) Or KeyboardPushed(#PB_Key_Escape)
    Exit = 1
  ElseIf KeyboardPushed(#PB_Key_R)
    Gosub InitTiles
    Delay(150)
  ElseIf KeyboardPushed(#PB_Key_S)
    Gosub Scramble 
  ElseIf KeyboardPushed(#PB_Key_A)
    MessageRequester(Title$,"By: Kenneth Cason"+Chr(10)+Chr(13)+"www.kennycason.com"+Chr(10)+Chr(13)+"Mouse - Click & Drag to shift columns & rows."+Chr(10)+Chr(13)+"Arrows - Shift Rows & Columns where the mouse is located."+Chr(10)+Chr(13)+"R - Reset"+Chr(10)+Chr(13)+"S - Scramble"+Chr(10)+Chr(13)+"Q - Quit")
  ElseIf KeyboardPushed(#PB_Key_Up)
    ShiftColumnUp(mX/TileDim)
    Delay(100)
  ElseIf KeyboardPushed(#PB_Key_Down)
    ShiftColumnDown(mX/TileDim)
    Delay(100)
  ElseIf KeyboardPushed(#PB_Key_Left)
    ShiftRowLeft(mY/TileDim)
    Delay(100)
  ElseIf KeyboardPushed(#PB_Key_Right)
    ShiftRowRight(mY/TileDim)   
    Delay(100)
  EndIf   
    
    
  Delay(1)
Until Exit = 1
End

  
;- InitTiles
InitTiles:
  Moves = 0
  Dim Tiles.b(Width, Height)
  
  For y = 0 To Height/2 - 1
    For x = 0 To Width/2 - 1
      Tiles(x,y) = #TILE_RED       
    Next 
  Next 
  
  For y = Height/2 To Height - 1
    For x = Width/2 To Width - 1
      Tiles(x,y) = #TILE_YELLOW     
    Next 
  Next 

  For y = 0 To Height/2 - 1
    For x = Width/2 To Width - 1
      Tiles(x,y) = #TILE_GREEN      
    Next 
  Next 
  
  For y = Height/2 To Height - 1
    For x = 0 To Width/2 - 1
      Tiles(x,y) = #TILE_BLUE    
    Next 
  Next 

Return 


Procedure ShiftColumnUp(column.b)
  Moves + 1
  temp.b = Tiles(column, 0)
  For y = 0 To Height - 2
    Tiles(column, y) = Tiles(column, y + 1)
  Next 
  Tiles(column, Height - 1) = temp  
EndProcedure 


Procedure ShiftColumnDown(column.b)
  Moves + 1
  temp.b = Tiles(column, Height - 1)
  For y = Height - 1 To 1 Step -1
    Tiles(column, y) = Tiles(column, y - 1)
  Next 
  Tiles(column, 0) = temp  
EndProcedure 

Procedure ShiftRowLeft(row.b)
  Moves + 1
  temp.b = Tiles(0, row)
  For x = 0 To Width - 2
    Tiles(x, row) = Tiles(x + 1, row)
  Next 
  Tiles(Width - 1, row) = temp  
EndProcedure 

Procedure ShiftRowRight(row.b)
  Moves + 1
  temp.b = Tiles(Width - 1, row)
  For x = Width - 1 To 1 Step -1
    Tiles(x, row) = Tiles(x - 1, row)
  Next 
  Tiles(0, row) = temp 
EndProcedure 

;- Scramble
Scramble:
  For x = 0 To 100
    r = Random(3)
    If r = 0
      ShiftRowRight(Random(Height-1))
    ElseIf r = 1
      ShiftRowLeft(Random(Height-1))
    ElseIf r = 2
      ShiftColumnDown(Random(Width-1))
    Else
      ShiftColumnUp(Random(Width-1))
    EndIf 
  Next 
  Moves = 0
Return 

; IDE Options = PureBasic 4.30 (Windows - x86)
; CursorPosition = 4
; Folding = -
; EnableXP
; UseIcon = icon.ico
; Executable = TileZ.exe