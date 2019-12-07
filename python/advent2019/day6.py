from operator import itemgetter
from itertools import groupby


def to_adj_lists(edges):
    sorted_edges = sorted(edges, key=itemgetter(0))
    return {
        k: [x[1] for x in g]
        for k, g in groupby(sorted_edges, key=itemgetter(0))
    }


def calc_distances(adj_lists, from_node):
    distances = {}

    def dfs(node, distance=0):
        to_list = adj_lists.get(node)
        if to_list:
            for to in to_list:
                distances[to] = distance + 1
                dfs(to, distance + 1)

    dfs(from_node)
    return distances


def total_orbits(edges):
    adj_lists = to_adj_lists(edges)
    distances = calc_distances(adj_lists, "COM")

    return sum(distances.values())


def distance_to_santa(edges):
    adj_lists = to_adj_lists([(y, x) for x, y in edges])
    from_you = calc_distances(adj_lists, "YOU")
    from_santa = calc_distances(adj_lists, "SAN")

    distances_between_you_and_santa = [
        distance + from_santa[to]
        for to, distance in from_you.items()
        if to in from_santa
    ]
    return min(distances_between_you_and_santa) - 2