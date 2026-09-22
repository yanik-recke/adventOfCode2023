from pathlib import Path
from dataclasses import dataclass, astuple, fields

@dataclass
class Conf:
    is_flip: bool
    on: bool
    inputs: dict[str, bool]
    targets: set[str]

    def __iter__(self):
        return (getattr(self, f.name) for f in fields(self))

# tuple[is_flipflop, is_on, conj_inputs, targets]
module_conf: dict[str, Conf] = {}

for line in Path("in20.txt").read_text().splitlines():
    name, targets = line.split(" -> ")
    targets = [x.strip() for x in targets.strip().split(",")]

    # flip flop
    if "%" in name:
        module_conf[name[1:]] = Conf(True, False, {}, set(targets))
    # conjunction
    elif "&" in name:
        module_conf[name[1:]] = Conf(False, False, {}, set(targets))
    # broadcast
    else:
        module_conf[name] = Conf(False, False, {}, set(targets))

module_conf["rx"] = Conf(False, False, {}, set())

for k in module_conf:
    for target in filter(lambda x: not module_conf[x].is_flip, module_conf[k].targets):
        module_conf[target].inputs[k] = False

lo = hi = 0
b = "broadcaster"
q: list[tuple[str, bool, str]] = []

for _ in range(0, 1000):
    q = [(e, False, b) for e in module_conf[b].targets]
    lo += 1
    while len(q) > 0:
        id, pulse, sender = q.pop(0)
        is_flip, on, inputs, targets = astuple(module_conf[id])

        # low pulse
        if not pulse:
            lo += 1
            if is_flip:
                on = not on
                for e in targets: q.append((e, on, id))
                module_conf[id].on = on
            else:
                inputs[sender] = pulse
                for e in targets: q.append((e, not all(inputs[i] for i in inputs), id))
                module_conf[id].inputs[sender] = pulse

        # high pulse
        else:
            hi += 1
            if not is_flip:
                inputs[sender] = pulse
                for e in targets: q.append((e, not all(inputs[i] for i in inputs), id))
                module_conf[id].inputs[sender] = pulse

print(f"hoch {hi} und niedrig {lo}")
print(hi * lo)