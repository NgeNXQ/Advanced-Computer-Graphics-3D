#define _USE_MATH_DEFINES
#include <math.h>
#include "framework.h"
#include "WindowsGDI.h"

#define MAX_LOADSTRING 100
#define APPLICATION_WINDOW_WIDTH 400
#define APPLICATION_WINDOW_HEIGHT 600

#define PREFERENCES_SUN_OFFSET_Y 125;
#define PREFERENCES_PYRAMID_OFFSET_X 75;
#define PREFERENCES_PYRAMID_OFFSET_Y 100;
#define PREFERENCES_PEN_FOREGROUND_THICKNESS 2
#define PREFERENCES_COLOR_BACKGROUND RGB(27, 27, 27)
#define PREFERENCES_COLOR_FOREGROUND RGB(255, 20, 147)

#define SHAPE_LINE_LENGTH 90
#define SHAPE_TRIANGLE_SIDE_SIZE 70
#define SHAPE_TRIANGLE_VERTICES_COUNT 3
#define SHAPE_POLYGON_RADIUS 45
#define SHAPE_POLYGON_VERTICES_COUNT 16

HINSTANCE hInst;
WCHAR szTitle[MAX_LOADSTRING];
WCHAR szWindowClass[MAX_LOADSTRING];

RECT clientRect;
HPEN penForeground;
HBRUSH brushForeground;
HBRUSH brushBackground;

ATOM MyRegisterClass(HINSTANCE);
BOOL InitInstance(HINSTANCE, int);
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);

void OnWmPaint(const HWND);
void HandleWmPaintDrawing(const HWND, const HDC);
void FillBackground(const HWND, const HDC, const HBRUSH);
void DrawTriangle(const HWND, const HDC, const HBRUSH);
void DrawPolygon(const HWND, const HDC, const HBRUSH);
void DrawRays(const HWND, const HDC, const HPEN);
inline POINT GetClientCenter(const RECT *const);

int APIENTRY wWinMain(_In_ HINSTANCE hInstance, _In_opt_ HINSTANCE hPrevInstance, _In_ LPWSTR lpCmdLine, _In_ int nCmdShow)
{
    UNREFERENCED_PARAMETER(hPrevInstance);
    UNREFERENCED_PARAMETER(lpCmdLine);

    LoadStringW(hInstance, IDS_APP_TITLE, szTitle, MAX_LOADSTRING);
    LoadStringW(hInstance, IDC_WINDOWSGDI, szWindowClass, MAX_LOADSTRING);
    MyRegisterClass(hInstance);

    if (!InitInstance(hInstance, nCmdShow))
        return FALSE;

    HACCEL hAccelTable = LoadAccelerators(hInstance, MAKEINTRESOURCE(IDC_WINDOWSGDI));

    MSG msg;

    while (GetMessage(&msg, NULL, 0, 0))
    {
        if (!TranslateAccelerator(msg.hwnd, hAccelTable, &msg))
        {
            TranslateMessage(&msg);
            DispatchMessage(&msg);
        }
    }

    return (int)msg.wParam;
}

//
//  FUNCTION: MyRegisterClass()
//
//  PURPOSE: Registers the window class.
//
ATOM MyRegisterClass(HINSTANCE hInstance)
{
    WNDCLASSEXW wcex;
    wcex.cbSize = sizeof(WNDCLASSEX);
    wcex.cbClsExtra = 0;
    wcex.cbWndExtra = 0;
    wcex.lpszMenuName = NULL;
    wcex.hbrBackground = NULL;
    wcex.hInstance = hInstance;
    wcex.lpfnWndProc = WndProc;
    wcex.lpszClassName = szWindowClass;
    wcex.style = CS_HREDRAW | CS_VREDRAW;
    wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
    wcex.hIcon = LoadIcon(hInstance, MAKEINTRESOURCE(IDI_WINDOWSGDI));
    wcex.hIconSm = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDI_SMALL));

    brushBackground = CreateSolidBrush(PREFERENCES_COLOR_BACKGROUND);
    brushForeground = CreateSolidBrush(PREFERENCES_COLOR_FOREGROUND);
    penForeground = CreatePen(PS_SOLID, PREFERENCES_PEN_FOREGROUND_THICKNESS, PREFERENCES_COLOR_FOREGROUND);

    return RegisterClassExW(&wcex);
}

//
//   FUNCTION: InitInstance(HINSTANCE, int)
//
//   PURPOSE: Saves instance handle and creates main window
//
//   COMMENTS:
//
//        In this function, we save the instance handle in a global variable and
//        create and display the main program window.
//
BOOL InitInstance(HINSTANCE hInstance, int nCmdShow)
{
    hInst = hInstance;

    DWORD windowStyles = WS_OVERLAPPED | WS_CAPTION | WS_SYSMENU | WS_MINIMIZEBOX;
    HWND hWnd = CreateWindowW(szWindowClass, szTitle, windowStyles, CW_USEDEFAULT, 0, APPLICATION_WINDOW_WIDTH, APPLICATION_WINDOW_HEIGHT, NULL, NULL, hInstance, NULL);

    if (!hWnd)
        return FALSE;

    ShowWindow(hWnd, nCmdShow);
    UpdateWindow(hWnd);

    return TRUE;
}

//
//  FUNCTION: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  PURPOSE: Processes messages for the main window.
//
//  WM_PAINT    - Paint the main window
//  WM_DESTROY  - post a quit message and return
//
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    switch (message)
    {
    case WM_PAINT:
        OnWmPaint(hWnd);
        break;
    case WM_DESTROY:
        DeleteObject(penForeground);
        DeleteObject(brushBackground);
        PostQuitMessage(EXIT_SUCCESS);
        break;
    default:
        return DefWindowProc(hWnd, message, wParam, lParam);
    }

    return EXIT_SUCCESS;
}

void __stdcall OnWmPaint(const HWND hWnd)
{
    PAINTSTRUCT ps;
    HDC hdc = BeginPaint(hWnd, &ps);
    GetClientRect(hWnd, &clientRect);
    HandleWmPaintDrawing(hWnd, hdc);
    EndPaint(hWnd, &ps);
}

void __stdcall HandleWmPaintDrawing(const HWND hWnd, const HDC hdc)
{
    SelectObject(hdc, GetStockObject(NULL_PEN));
    SelectObject(hdc, GetStockObject(NULL_BRUSH));

    FillBackground(hWnd, hdc, brushBackground);
    DrawTriangle(hWnd, hdc, brushForeground);
    DrawPolygon(hWnd, hdc, brushForeground);
    DrawRays(hWnd, hdc, penForeground);
}

void __stdcall FillBackground(const HWND hWnd, const HDC hdc, const HBRUSH brush)
{
    SelectObject(hdc, brush);
    FillRect(hdc, &clientRect, brush);
}

void __stdcall DrawTriangle(const HWND hWnd, const HDC hdc, const HBRUSH brush)
{
    POINT center = GetClientCenter(&clientRect);
    POINT vertices[SHAPE_TRIANGLE_VERTICES_COUNT];

    center.x += PREFERENCES_PYRAMID_OFFSET_X;
    center.y += PREFERENCES_PYRAMID_OFFSET_Y;

    // Top vertex
    vertices[0].x = center.x;
    vertices[0].y = center.y - SHAPE_TRIANGLE_SIDE_SIZE;

    // Bottom left
    vertices[1].x = center.x - SHAPE_TRIANGLE_SIDE_SIZE;
    vertices[1].y = center.y + SHAPE_TRIANGLE_SIDE_SIZE;

    // Bottom right
    vertices[2].x = center.x + SHAPE_TRIANGLE_SIDE_SIZE;
    vertices[2].y = center.y + SHAPE_TRIANGLE_SIDE_SIZE;

    SelectObject(hdc, brush);
    Polygon(hdc, vertices, SHAPE_TRIANGLE_VERTICES_COUNT);
}

void __stdcall DrawPolygon(const HWND hWnd, const HDC hdc, const HBRUSH brush)
{
    POINT center = GetClientCenter(&clientRect);
    center.y -= PREFERENCES_SUN_OFFSET_Y;

    POINT vertices[SHAPE_POLYGON_VERTICES_COUNT];

    for (size_t i = 0; i < SHAPE_POLYGON_VERTICES_COUNT; ++i)
    {
        vertices[i].x = SHAPE_POLYGON_RADIUS * cos(2 * M_PI * i / SHAPE_POLYGON_VERTICES_COUNT) + center.x;
        vertices[i].y = SHAPE_POLYGON_RADIUS * sin(2 * M_PI * i / SHAPE_POLYGON_VERTICES_COUNT) + center.y;
    }

    SelectObject(hdc, brush);
    Polygon(hdc, vertices, SHAPE_POLYGON_VERTICES_COUNT);
}

void __stdcall DrawRays(const HWND hWnd, const HDC hdc, const HPEN pen)
{
    float angle;
    int lineEndX;
    int lineEndY;

    POINT center = GetClientCenter(&clientRect);
    center.y -= PREFERENCES_SUN_OFFSET_Y;

    SelectObject(hdc, pen);

    for (size_t i = 0; i < SHAPE_POLYGON_VERTICES_COUNT; ++i)
    {
        angle = 2 * M_PI * i / SHAPE_POLYGON_VERTICES_COUNT;
        lineEndX = center.x + SHAPE_LINE_LENGTH * cos(angle);
        lineEndY = center.y + SHAPE_LINE_LENGTH * sin(angle);

        MoveToEx(hdc, center.x, center.y, NULL);
        LineTo(hdc, lineEndX, lineEndY);
    }
}

inline POINT GetClientCenter(const RECT *const clientRect)
{
    POINT center;
    center.x = (clientRect->right - clientRect->left) / 2;
    center.y = (clientRect->bottom - clientRect->top) / 2;
    return center;
}
