from pathlib import Path

lines = Path("in21.txt").read_text().splitlines()
w, h = len(lines[0]), len(lines)

garden = {
    (c, r): 0 for r, l in enumerate(lines) for c, s in enumerate(l) if s == "." or s == "S"
}

S = (w // 2, h // 2)
print(S)

# 65 = 3734, 196 = 33285, 327 = 92268
steps = 1

def g(n):
    return 3734 + n * (33285 - 3734) + n * (n - 1) // 2 * (92268 - 2 * 33285 + 3734)

print(g(202300))

visited: set[tuple[int, int]] = set()
in_process: set[tuple[int, tuple[int, int]]] = set()
dirs = [(0, -1), (1, 0), (0, 1), (-1, 0)]

def inf(pos: tuple[int, int]) -> bool:
    x, y = pos

    if x < 0: 
        x = w - (abs(x) % w)
    if y < 0:
        y = h - (abs(y) % h)
    if x >= w:
        x = x % w
    if y >= h:
        y = y % h

    return (x, y) in garden

def f(steps: int, pos: tuple[int, int]):
    if steps == 0:
        visited.add(pos)
        return

    for dx, dy in dirs:
        nx, ny = pos[0] + dx, pos[1] + dy

        if inf((nx, ny)):
            if (steps - 1, (nx,  ny)) not in in_process:
                in_process.add((steps - 1, (nx, ny)))
                f(steps - 1, (nx, ny))

f(steps, S)
print(len(visited))