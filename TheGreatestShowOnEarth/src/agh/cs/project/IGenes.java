package agh.cs.project;

public interface IGenes {
    /**
     * Takes another Genes to crossbreed with and produces new Genes of the same type as combination of given two.
     * @param gene  Genes of the same type to crossbreed with
     * @return New Genes made by crossbreeding
     */
    IGenes crossBreed(Object gene);

    /**
     * Makes the decision based on its configuration of genes.
     * @return Number from -128 to 127 representing the decision.
     */
    byte makeDecision();
}
