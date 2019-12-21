package agh.cs.project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RotationGenes implements IGenes {
    private static final int GENES_SIZE = 32;
    private static final int CROSSBREEDING_GENES_CHUNKS_COUNT = 3;
    private static final int[] POSSIBLE_GENE_VALUES = {0, 1, 2, 3, 4, 5, 6, 7};

    private List<Integer> genes;

    public RotationGenes() {
        genes = new ArrayList<Integer>();
        for(int i = 0; i < GENES_SIZE; i++) {
            genes.add(POSSIBLE_GENE_VALUES[ThreadLocalRandom.current().nextInt(0, POSSIBLE_GENE_VALUES.length)]);
        }

        fixGenes();
        genes.sort(Integer::compareTo);
    }

    private RotationGenes(List<Integer> genes) {
        this.genes = genes;

        fixGenes();
        this.genes.sort(Integer::compareTo);
    }

    @Override
    public IGenes crossBreed(Object genes) {
        if(!(genes instanceof RotationGenes)) {
            System.out.println("Wrong argument - created random RotationGenes");
            return new RotationGenes();
        }
        var otherGenes = ((RotationGenes) genes).genes;

        var genePool = new ArrayList<List<Integer>>(List.of(this.genes, otherGenes));

        var geneChunkPoolsSequence = drawGeneChunkPoolsSequence(genePool.size()); //ex. [1, 0, 1] for CROSSBREEDING_GENES_CHUNKS_COUNT=3
        var geneChunkLengths = drawGeneChunkLengths(); //ex. [7, 1, 24] for GENES_SIZE=32, CROSSBREEDING_GENES_CHUNKS_COUNT=3
        var newGenes = new ArrayList<Integer>();

        for(int i = 0; i < CROSSBREEDING_GENES_CHUNKS_COUNT; i++) {
            var genesToTakeChunkFrom = genePool.get(geneChunkPoolsSequence.get(i));
            var chunkLength = (long) geneChunkLengths.get(i);

            //append chunk to newGenes
            newGenes.addAll(genesToTakeChunkFrom.stream().limit(chunkLength).collect(Collectors.toList()));

            //trim all genes in pool
            for(int j = 0; j < genePool.size(); j++) {
                genePool.set(j, genePool.get(j).stream().skip(chunkLength).collect(Collectors.toList()));
            }
        }

        return new RotationGenes(newGenes);
    }

    @Override
    public byte makeDecision() {
        var drawnGene = genes.get(ThreadLocalRandom.current().nextInt(0, GENES_SIZE));
        return mapGeneValueToByte(drawnGene);
    }

    private byte mapGeneValueToByte(Integer val) {
        return (byte)( (val*(Byte.MAX_VALUE - Byte.MIN_VALUE + 1)) / POSSIBLE_GENE_VALUES.length + Byte.MIN_VALUE );
    }

    private void fixGenes() {
        for(var geneValue : POSSIBLE_GENE_VALUES) {
            if(genes.stream().noneMatch(g -> g == geneValue)) {
                setAnyOfIntegerListDuplicatesToValue(genes, geneValue);
            }
        }
    }

    private List<Integer> drawGeneChunkPoolsSequence(int poolSize) {
        var sequence = new ArrayList<Integer>();

        for(int i = 0; i < CROSSBREEDING_GENES_CHUNKS_COUNT; i++) {
            sequence.add(ThreadLocalRandom.current().nextInt(0, poolSize));
        }

        //ensure chunks of all genes from pool are present
        for(int i = 0; i < poolSize; i++) {
            var genesIndex = i;
            if(sequence.stream().noneMatch(g -> g == genesIndex)) {
                setAnyOfIntegerListDuplicatesToValue(sequence, genesIndex);
            }
        }
        return sequence;
    }

    /**
     * Generates List of size equal to CROSSBREEDING_GENES_CHUNKS_COUNT with Integers >= 1 which all sum up to GENES_SIZE
     * @return
     */
    private List<Integer> drawGeneChunkLengths() {
        var lengths = new ArrayList<Integer>();
        var remainingGenesCount = GENES_SIZE;

        //draw first n-1 lengths
        for(int i = 0; i < CROSSBREEDING_GENES_CHUNKS_COUNT - 1; i++) {
            var length = ThreadLocalRandom.current().nextInt(1, remainingGenesCount - CROSSBREEDING_GENES_CHUNKS_COUNT + i + 2);
            remainingGenesCount -= length;
            lengths.add(length);
        }
        //last chunk contains all remaining genes
        lengths.add(remainingGenesCount);
        return lengths;
    }

    /**
     * Changes random list element which occurs more than once to given value.
     * @param list
     * @param value
     */
    private void setAnyOfIntegerListDuplicatesToValue(List<Integer> list, int value) {
        var valuesOccurringMoreThanOnce = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .map(e -> e.getKey())
                .collect(Collectors.toList());

        var valueToBeReplaced = valuesOccurringMoreThanOnce.stream()
                .skip((int) (valuesOccurringMoreThanOnce.size() * Math.random()))
                .findFirst()
                .get();

        list.set(list.indexOf(valueToBeReplaced), value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof RotationGenes))
            return false;
        return this.genes.equals(((RotationGenes) other).genes);
    }

    @Override
    public int hashCode() {
        var odds = new int[] {17, 19, 21, 23, 25, 27, 29, 31};
        int hash = 13;
        for(int i = 0; i< POSSIBLE_GENE_VALUES.length; i++) {
            var value = POSSIBLE_GENE_VALUES[i];
            hash += genes.stream().filter(a -> a == value).count() * odds[i%odds.length];
        }
        return hash;
    }

    @Override
    public String toString() {
        return genes.stream().map(String::valueOf).collect(Collectors.joining(" "));
    }
}
