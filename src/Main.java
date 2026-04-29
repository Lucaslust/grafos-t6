public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException(
                    "informe dois arquivos de entrada. Ex.: java Main ../dados/arvore1.txt ../dados/arvore2.txt"
            );
        }

        Graph tree1 = new Graph(new In(args[0]));
        Graph tree2 = new Graph(new In(args[1]));

        StdOut.println("============================================================");
        StdOut.println("Arvore 1 (" + args[0] + ")");
        StdOut.println("============================================================");
        StdOut.println(tree1);

        StdOut.println("============================================================");
        StdOut.println("Arvore 2 (" + args[1] + ")");
        StdOut.println("============================================================");
        StdOut.println(tree2);

        TreeIsomorphism analysis1 = new TreeIsomorphism(tree1);
        TreeIsomorphism analysis2 = new TreeIsomorphism(tree2);

        StdOut.println("------------------------------------------------------------");
        StdOut.println("Validacao das entradas");
        StdOut.println("------------------------------------------------------------");
        StdOut.println("Arvore 1: " + analysis1.getValidationMessage());
        StdOut.println("Arvore 2: " + analysis2.getValidationMessage());
        StdOut.println();

        if (!analysis1.isTree() || !analysis2.isTree()) {
            StdOut.println("Comparacao interrompida: ao menos uma das entradas nao e uma arvore valida.");
            return;
        }

        StdOut.println("------------------------------------------------------------");
        StdOut.println("Centros");
        StdOut.println("------------------------------------------------------------");
        StdOut.println("Arvore 1: " + formatCenters(analysis1.getCenters()));
        StdOut.println("Arvore 2: " + formatCenters(analysis2.getCenters()));
        StdOut.println();

        StdOut.println("------------------------------------------------------------");
        StdOut.println("Codificacao canonica");
        StdOut.println("------------------------------------------------------------");
        StdOut.println("Arvore 1: " + analysis1.getCanonicalEncoding());
        StdOut.println("Arvore 2: " + analysis2.getCanonicalEncoding());
        StdOut.println();

        boolean isomorfas = TreeIsomorphism.areIsomorphic(analysis1, analysis2);
        StdOut.println("============================================================");
        StdOut.println("Veredito: as duas arvores " + (isomorfas ? "SAO" : "NAO SAO") + " isomorfas");
        StdOut.println("============================================================");
    }

    private static String formatCenters(int[] centers) {
        StringBuilder sb = new StringBuilder();
        sb.append(centers.length == 1 ? "1 centro" : centers.length + " centros");
        sb.append(" -> {");
        for (int i = 0; i < centers.length; i++) {
            if (i > 0) sb.append(", ");
            sb.append(centers[i]);
        }
        sb.append("}");
        return sb.toString();
    }
}
