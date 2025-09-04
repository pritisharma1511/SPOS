#include <iostream>
#include <vector>
#include <limits>
using namespace std;

const int INF = 1e9; // A large value representing infinity

void dijkstra(int n, vector<vector<int>> &cost, int src) {
    vector<int> dist(n, INF);
    vector<bool> visited(n, false);
    dist[src] = 0;

    for (int i = 0; i < n; ++i) {
        int u = -1;

        // Find the unvisited node with the smallest distance
        for (int j = 0; j < n; ++j)
            if (!visited[j] && (u == -1 || dist[j] < dist[u]))
                u = j;

        visited[u] = true;

        // Update distances to neighbors
        for (int v = 0; v < n; ++v)
            if (cost[u][v] && dist[u] + cost[u][v] < dist[v])
                dist[v] = dist[u] + cost[u][v];
    }

    // Print the shortest distances
    cout << "\nShortest paths from node " << src << ":\n";
    for (int i = 0; i < n; ++i)
        cout << "  to node " << i << ": " << dist[i] << "\n";
}

int main() {
    int n;
    cout << "Enter number of nodes: ";
    cin >> n;

    vector<vector<int>> cost(n, vector<int>(n));

    cout << "Enter cost matrix (0 if no direct connection):\n";
    for (int i = 0; i < n; ++i)
        for (int j = 0; j < n; ++j) {
            cin >> cost[i][j];
            if (cost[i][j] == 0 && i != j)
                cost[i][j] = INF;
        }

    int src;
    cout << "Enter source node: ";
    cin >> src;

    dijkstra(n, cost, src);
    return 0;
}

