package jimp.grafyprogram.graphutils;

import jimp.grafyprogram.graphutils.Edge;
import jimp.grafyprogram.graphutils.Graph;
import jimp.grafyprogram.graphutils.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;

abstract public class GraphReader {






    abstract public Graph read() throws IOException;
}
