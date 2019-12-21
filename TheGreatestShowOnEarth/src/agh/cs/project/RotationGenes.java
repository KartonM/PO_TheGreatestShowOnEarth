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
//        for(var geneValue : genes) {
//            System.out.print(geneValue.toString() + " ");
//        }
//        System.out.println("\n");
    }

    private RotationGenes(List<Integer> genes) {
        this.genes = genes;

        fixGenes();
        this.genes.sort(Integer::compareTo);
//        for(var geneValue : this.genes) {
//            System.out.print(geneValue.toString() + " ");
//        }
//        System.out.println("\n");
    }

    @Override
    public IGenes crossBreed(Object genes) {
        if(!(genes instanceof RotationGenes)) {
            System.out.println("Wrong argument - created random RotationGenes");
            return new RotationGenes();
        }
        var otherGenes = ((RotationGenes) genes).genes;

        var genePool = new ArrayList<List<Integer>>(List.of(this.genes, otherGenes));

        var geneChunkPoolsSequence = drawGeneChunkPoolsSequence(genePool.size());
        var geneChunkLengths = drawGeneChunkLengths();
        var newGenes = new ArrayList<Integer>();

        for(int i = 0; i < CROSSBREEDING_GENES_CHUNKS_COUNT; i++) {
            var genesToTakeChunkFrom = genePool.get(geneChunkPoolsSequence.get(i));
            var chunkLength = (long) geneChunkLengths.get(i);

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
            if(!genes.stream().anyMatch(g -> g == geneValue)) {
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
            if(!sequence.stream().anyMatch(g -> g == genesIndex)) {
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
}
