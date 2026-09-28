from pathlib import Path

lines = Path("in.txt").read_text().splitlines()
w, h = len(lines[0]), len(lines)

garden = {
    (c, r): 0 for r, l in enumerate(lines) for c, s in enumerate(l) if s == "." or s == "S"
}

S = (w // 2, h // 2)
print(S)

steps = 64

visited: set[tuple[int, int]] = set()

in_process: set[tuple[int, tuple[int, int]]] = set()

dirs = [(0, -1), (1, 0), (0, 1), (-1, 0)]

def f(steps: int, pos: tuple[int, int]):
    if steps == 0:
        visited.add(pos)
        return

    for dx, dy in dirs:
        nx, ny = pos[0] + dx, pos[1] + dy

        if (nx, ny) in garden:
            if (steps - 1, (nx, ny)) not in in_process:
                in_process.add((steps - 1, (nx, ny)))
                f(steps - 1, (nx, ny))

f(steps, S)
print(len(visited))