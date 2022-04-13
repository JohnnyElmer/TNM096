#include "puzzle.h"

/**Cleaner version**/
#include <iostream>
#include <string>
#include <queue>
#include <vector>
#include <algorithm>
#include <set>
using namespace std;

using State = int[3][3];

//The goal state. Global to ease access for now.
const int goal[3][3]{
	{1, 2, 3},
	{4, 5, 6},
	{7, 8, 0}
};

//Struct to represent a board
struct Node
{
	//The current state of the puzzle board.
	int board[3][3]{
		{ 6, 4, 7 },
		{ 8, 5, 0 },
		{ 3, 2, 1 }
	};

	//Heuristics and cost
	int _c = 0; //Cost from all earlier moves
	int _h = 0; // the heuristic for the current node
	int _cost = 0; //The total cost of moving -> _c + _h;

	//String containing the movements uptil the current node
	//Each movement is added as a letter to the string
	string _movement = "";

	//Operator to be able to compare two boards.
	bool operator==(Node& const n2) {
		return board == n2.board;
	}

	//Make a string of all the numbers on our board.
	string makeString() {
		string res = "";
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				res += to_string(board[i][j]);
			}
		}
		return res;
	}

};

struct Compare_Cost {
	bool operator()(Node const& n1, Node const& n2) {
		return n1._cost > n2._cost;
	}
};

//Checks if a nodes state matches the goal state.
bool IsGoal(Node const& n) {
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
			if (n.board[i][j] != goal[i][j])
				return false;
		}
	}
	return true;
}

//Finds the index in the goal state
pair<int, int> Find_Index(int val) {
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
			if (goal[i][j] == val) {
				return pair<int, int>(i, j);
			}

		}
	}
}

//Calculate heuristic for one node. The heuristic is the sum of all tiles different
//from the goal board.
void Heuristic(Node& n) {

	//h1
	int counter = 0;
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
			if (n.board[i][j] != goal[i][j]) {
				++counter;
			}
		}
	}
	n._h = counter;
}

void Compute_Cost(Node& n) {
	n._cost = n._c + n._h;
}

vector<Node> Generate_Successors(Node& n) {

	vector<Node> children;

	//generate successors
	int row = 0, col = 0;

	for (int i = 0; i < 3; ++i) {
		for (int j = 0; j < 3; ++j) {

			if (n.board[i][j] == 0) {
				row = i;
				col = j;
				break;
			}
		}
	}

	//Move up
	if (row > 0) {
		Node child = n;
		std::swap(child.board[row][col], child.board[row - 1][col]);
		child._movement += "U";
		++child._c;
		Heuristic(child);
		children.push_back(child);
	}

	//Move down
	if (row < 2) {
		Node child = n;
		std::swap(child.board[row][col], child.board[row + 1][col]);
		child._movement += "D";
		++child._c;
		Heuristic(child);
		children.push_back(child);
	}

	//Move to the left
	if (col > 0) {
		Node child = n;
		std::swap(child.board[row][col], child.board[row][col - 1]);
		child._movement += "L";
		++child._c;
		Heuristic(child);
		children.push_back(child);
	}

	//Move to the right
	if (col < 2) {
		Node child = n;
		std::swap(child.board[row][col], child.board[row][col + 1]);
		child._movement += "R";
		++child._c;
		Heuristic(child);
		children.push_back(child);
	}
	return children;
}


string A_Star(Node start) {

	//Instantiate the open list
	priority_queue<Node, vector<Node>, Compare_Cost> open_list;
	open_list.push(start);

	//Instantiate the closed list
	set<string> closed_list;

	while (!open_list.empty()) {
		//Retrieve the first node in the queue
		Node first = open_list.top();
		open_list.pop();
		closed_list.emplace(first.makeString()); //the node has been visited

		if (IsGoal(first)) {
			return first._movement;
		}
		else {
			//Generate children for the node;
			vector<Node> children = Generate_Successors(first);
			//cout << "-------------" << endl;
			for (auto s : children) {
				if (closed_list.find(s.makeString()) != closed_list.end()) {
					continue;
				}
				Compute_Cost(s);
				open_list.push(s);
			}
		}
	}

	return "No solution found.";
}




int main() {
	cout << "Starting puzzle" << endl;
	Node start;


	priority_queue<Node, vector<Node>, Compare_Cost> open_list;
	open_list.push(start);

	vector<Node> c = Generate_Successors(start);

	string res = A_Star(start);
	cout << "Solution found. Number of steps: " << res.size() << endl;
	cout << "Moves: " << res << endl;
	cout << "Puzzle complete.";

	int n;
	cin >> n;
}
